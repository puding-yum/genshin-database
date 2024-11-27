package com.yummy.puding.genshin.database.database.genshin.service;

import com.yummy.puding.genshin.database.database.genshin.model.client.req.AddElementReq;
import com.yummy.puding.genshin.database.database.genshin.model.client.req.RegisterReq;
import com.yummy.puding.genshin.database.database.genshin.model.client.res.AddElementRes;
import com.yummy.puding.genshin.database.database.genshin.model.client.res.ErrorRes;
import com.yummy.puding.genshin.database.database.genshin.model.dbi.ElementDbi;
import com.yummy.puding.genshin.database.database.genshin.repository.ElementRepository;
import com.yummy.puding.genshin.database.entity.ElementEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementService {

    @Autowired
    private ElementRepository elementRepository;

    public ResponseEntity<Object> getAll() {
        try{
            List<ElementDbi> elements = elementRepository.selectAll();

            return new ResponseEntity<>(elements, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorRes errorRes = new ErrorRes();
            errorRes.setMessage(e.getMessage());
            return new ResponseEntity<>(errorRes, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> add(AddElementReq request) {
        try{
            ElementEntity elementEntity = new ElementEntity();
            elementEntity.setName(request.getName());
            elementRepository.save(elementEntity);

            AddElementRes addElementRes = new AddElementRes();
            addElementRes.setMessage("Success add element");

            return new ResponseEntity<>(addElementRes, HttpStatus.CREATED);
        }catch (Exception e){
            ErrorRes errorRes = new ErrorRes();
            errorRes.setMessage(e.getMessage());
            return new ResponseEntity<>(errorRes, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
