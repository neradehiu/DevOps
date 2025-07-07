package com.atp.fwfe.service.work;

import com.atp.fwfe.dto.work.*;
import com.atp.fwfe.model.account.Account;
import com.atp.fwfe.model.work.Company;
import com.atp.fwfe.repository.account.AccRepository;
import com.atp.fwfe.repository.work.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    public class CompanyService {
        private final CompanyRepository companyRepo;
        private final AccRepository accountRepo;

        public CompanyResponse create(CreateCompanyDto dto, String username) {
            Account creator = accountRepo.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("Account not found"));
            Company company = Company.builder()
                    .name(dto.getName())
                    .descriptionCompany(dto.getDescriptionCompany())
                    .type(dto.getType())
                    .address(dto.getAddress())
                    .isPublic(dto.getIsPublic() != null ? dto.getIsPublic() : true)
                    .createdBy(creator)
                    .build();
            Company saved = companyRepo.save(company);
            return mapToResponse(saved);
        }

        public List<CompanyResponse> getAll(String username, String role) {
            if ("ROLE_WORK".equals(role)) {
                Account acc = accountRepo.findByUsername(username)
                        .orElseThrow(() -> new EntityNotFoundException("Account not found"));
                return companyRepo.findByCreatedById(acc.getId()).stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());
            }
            return companyRepo.findAll().stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        }

        public CompanyResponse getOne(Long id, String username, String role) {
            Company company = companyRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Company not found"));
            if ("ROLE_WORK".equals(role) && !company.getCreatedBy().getUsername().equals(username)) {
                throw new SecurityException("Access denied");
            }
            return mapToResponse(company);
        }

        public CompanyResponse update(Long id, CreateCompanyDto dto, String username, String role) {
            Company company = companyRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Company not found"));
            if ("ROLE_WORK".equals(role) && !company.getCreatedBy().getUsername().equals(username)) {
                throw new SecurityException("Access denied");
            }
            company.setName(dto.getName());
            company.setDescriptionCompany(dto.getDescriptionCompany());
            company.setType(dto.getType());
            company.setAddress(dto.getAddress());
            company.setIsPublic(dto.getIsPublic() != null ? dto.getIsPublic() : company.getIsPublic());
            return mapToResponse(companyRepo.save(company));
        }

        public void delete(Long id, String username, String role) {
            Company company = companyRepo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Company not found"));
            if ("ROLE_WORK".equals(role) && !company.getCreatedBy().getUsername().equals(username)) {
                throw new SecurityException("Access denied");
            }
            companyRepo.delete(company);
        }

        public List<CompanyResponse> search(String keyword) {
            return companyRepo.searchAllFields(keyword).stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        }

        private CompanyResponse mapToResponse(Company c) {
            CompanyResponse r = new CompanyResponse();
            r.setId(c.getId());
            r.setName(c.getName());
            r.setDescriptionCompany(c.getDescriptionCompany());
            r.setType(c.getType());
            r.setAddress(c.getAddress());
            r.setIsPublic(c.getIsPublic());
            r.setCreatedById(c.getCreatedBy().getId());
            r.setCreatedByUsername(c.getCreatedBy().getUsername());
            return r;
        }
    }
