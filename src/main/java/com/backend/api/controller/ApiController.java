package com.backend.api.controller;

import com.backend.api.requestModel.FilterModel;
import com.backend.api.requestModel.RequestModel;
import com.backend.api.responseModel.ResponseModel;
import com.backend.api.responseModel.UserResponse;
import com.backend.api.searchmodel.ArrayItems;
import com.backend.api.searchmodel.Response;
import com.backend.api.ControllerService.ApiService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ApiController {
   @Autowired
   private ApiService apiService;

    @PostMapping(value="/search")
    public UserResponse getAll(@RequestBody RequestModel obj)
    {

        return apiService.getAllObjectsUtil(obj.type,obj.keyword,obj.filter,obj.size,obj.after,obj.next);
    }





}
