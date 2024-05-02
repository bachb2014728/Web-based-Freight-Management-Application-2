package com.dev.backend.service;

import com.dev.backend.rest.dto.MerchandiseResponse;
import com.dev.backend.rest.dto.MerchandiseUpdateRequest;
import com.dev.backend.rest.dto.address.InformationRequest;
import com.dev.backend.web.dto.CreateMerchandise;
import com.dev.backend.web.dto.EditMerchandise;
import com.dev.backend.web.dto.MerchandiseDto;
import com.dev.backend.web.dto.MerchandiseUserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface MerchandiseService {
    List<MerchandiseDto> getAllMerchandise(Principal principal);

    void saveNewMerchandise(CreateMerchandise merchandise, MultipartFile[] photos) throws IOException;

    MerchandiseDto getOneMerchandise(String id);

    void deleteOneMerchandise(String id);

    EditMerchandise getOneMerchandiseEdit(String id);

    void updateMerchandise(EditMerchandise merchandise, String id,MultipartFile[] photos) throws IOException;

    Object updateMerchandiseOfUser(MerchandiseUpdateRequest merchandise, String id);

    Object deleteAllMerchandisesOfUser(HttpServletRequest request);

    List<MerchandiseResponse> getAllMerchandiseOfUser(HttpServletRequest request);

    Object deleteOneMerchandiseById(String id);

    MerchandiseDto saveNewMerchandiseByUser(CreateMerchandise merchandise, HttpServletRequest request);

    Object updateMerchandiseAddressByUser(String id, InformationRequest informationRequest);

    MerchandiseUserDto getOneMerchandiseByUser(String id);

    List<MerchandiseDto> getAllMerchandiseHaveProcessing(Principal principal);

    List<MerchandiseDto> getAllMerchandiseHaveProcessingAndPending(Principal principal);
}
