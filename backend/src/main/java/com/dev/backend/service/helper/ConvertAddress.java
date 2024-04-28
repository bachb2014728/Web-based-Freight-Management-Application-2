package com.dev.backend.service.helper;

import com.dev.backend.document.District;
import com.dev.backend.document.Province;
import com.dev.backend.document.Ward;
import com.dev.backend.repository.DistrictRepository;
import com.dev.backend.repository.ProvinceRepository;
import com.dev.backend.repository.WardRepository;
import com.dev.backend.web.dto.location.Address;
import com.dev.backend.web.dto.location.DistrictDto;
import com.dev.backend.web.dto.location.ProvinceDto;
import com.dev.backend.web.dto.location.WardDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ConvertAddress {
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;
    @NotNull
    public Address getAddress(ProvinceDto provinceRoot, DistrictDto districtRoot , WardDto wardRoot) {
        Address address = Address.builder().build();

        if(!provinceRepository.existsByName(provinceRoot.getName())){
            Ward ward = Ward.builder().code(wardRoot.getCode()).name(wardRoot.getName()).build();
            Ward wardNew = wardRepository.save(ward);
            District district = District
                    .builder().name(districtRoot.getName())
                    .code(districtRoot.getCode())
                    .wards(Collections.singletonList(wardNew))
                    .build();
            District districtNew  = districtRepository.save(district);
            Province province = Province.builder()
                    .name(provinceRoot.getName())
                    .code(provinceRoot.getCode())
                    .districts(Collections.singletonList(districtNew))
                    .build();
            Province provinceNew = provinceRepository.save(province);
            address.setWard(wardNew);
            address.setDistrict(districtNew);
            address.setProvince(provinceNew);
        }else if (!districtRepository.existsByName(districtRoot.getName())){
            Ward ward = Ward.builder().name(wardRoot.getName()).code(wardRoot.getCode()).build();
            Ward wardNew = wardRepository.save(ward);
            District district = District
                    .builder().name(districtRoot.getName())
                    .code(districtRoot.getCode())
                    .wards(Collections.singletonList(wardNew))
                    .build();
            District districtNew  = districtRepository.save(district);
            Province province = provinceRepository.findByName(provinceRoot.getName()).get();
            province.getDistricts().add(districtNew);
            Province provinceNew = provinceRepository.save(province);
            address.setWard(wardNew);
            address.setDistrict(districtNew);
            address.setProvince(provinceNew);
        }else{
            Ward ward = Ward.builder().name(wardRoot.getName()).code(wardRoot.getCode()).build();
            Ward wardNew = wardRepository.save(ward);
            District district = districtRepository.findByName(districtRoot.getName()).get();
            district.getWards().add(wardNew);
            District districtNew = districtRepository.save(district);
            Province province = provinceRepository.findByName(provinceRoot.getName()).get();
            address.setWard(wardNew);
            address.setDistrict(districtNew);
            address.setProvince(province);
        }
        return address;
    }
    public Address getAddressTow(ProvinceDto provinceRoot, DistrictDto districtRoot , WardDto wardRoot) {
        Address address = Address.builder().build();
        Ward ward ;
        District district ;
        Province province ;
        if(wardRepository.existsByName(wardRoot.getName())){
            ward = wardRepository.findByName(wardRoot.getName());
            address.setWard(ward);
        }else{
            Ward wardNew = Ward.builder().code(wardRoot.getCode()).name(wardRoot.getName()).build();
            ward = wardRepository.save(wardNew);
            address.setWard(ward);
        }

        if(districtRepository.existsByName(districtRoot.getName())){
            district = districtRepository.findByName(districtRoot.getName()).get();
            address.setDistrict(district);
            if(checkExistence(district.getWards(), ward.getName())){
                district.getWards().add(ward);
                districtRepository.save(district);
            }
        }else{
            District districtNew = District.builder()
                    .name(districtRoot.getName()).code(districtRoot.getCode())
                    .wards(Collections.singletonList(ward)).build();
            district  = districtRepository.save(districtNew);
            address.setDistrict(district);
        }

        if (provinceRepository.existsByName(provinceRoot.getName())){
            province = provinceRepository.findByName(provinceRoot.getName()).get();
            if(checkDistrictExistence(province.getDistricts(), district.getName())){
                province.getDistricts().add(district);
                provinceRepository.save(province);
            }
            address.setProvince(province);
        }else{
            Province provinceNew = Province.builder()
                    .name(provinceRoot.getName())
                    .code(provinceRoot.getCode())
                    .districts(Collections.singletonList(district))
                    .build();
            province = provinceRepository.save(provinceNew);
            address.setProvince(province);
        }
        return address;
    }
    public boolean checkExistence(List<Ward> wards, String ward) {
        return !wards.contains(ward);
    }
    public boolean checkDistrictExistence(List<District> districts, String district) {
        return !districts.contains(district);
    }
}
