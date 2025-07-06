package com.atp.fwfe.service.work;

import com.atp.fwfe.repository.account.AccRepository;
import com.atp.fwfe.repository.work.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final AccRepository accRepository;


}
