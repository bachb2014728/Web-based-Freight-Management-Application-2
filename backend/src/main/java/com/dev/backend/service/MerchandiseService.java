package com.dev.backend.service;

import com.dev.backend.web.dto.CreateMerchandise;
import com.dev.backend.web.dto.EditMerchandise;
import com.dev.backend.web.dto.MerchandiseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MerchandiseService {
    List<MerchandiseDto> getAllMerchandise();

    void saveNewMerchandise(CreateMerchandise merchandise, MultipartFile[] photos) throws IOException;

    MerchandiseDto getOneMerchandise(String id);

    void deleteOneMerchandise(String id);

    EditMerchandise getOneMerchandiseEdit(String id);

    void updateMerchandise(EditMerchandise merchandise, String id,MultipartFile[] photos) throws IOException;
}
