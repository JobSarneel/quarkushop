package nl.spacedebris.monolith.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import nl.spacedebris.monolith.model.Address;
import nl.spacedebris.monolith.repository.web.dto.AddressDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressService {

    public static Address createFromDto(AddressDto addressDto) {
        return new Address(
                addressDto.getAddress1(),
                addressDto.getAddress2(),
                addressDto.getCity(),
                addressDto.getPostcode(),
                addressDto.getCountry()
        );
    }

    public static AddressDto mapToDto(Address address) {
        return new AddressDto(
                address.getAddress1(),
                address.getAddress2(),
                address.getCity(),
                address.getPostcode(),
                address.getCountry()
        );

    }
}