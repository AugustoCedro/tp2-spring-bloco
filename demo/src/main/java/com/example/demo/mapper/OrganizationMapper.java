package com.example.demo.mapper;


import com.example.demo.dto.OrganizationResponseDTO;
import com.example.demo.model.audit.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    public OrganizationResponseDTO toOrganizationResponseDTO(Organization organization){
        return new OrganizationResponseDTO(
                organization.getName(),
                organization.getActive()
        );
    }

}
