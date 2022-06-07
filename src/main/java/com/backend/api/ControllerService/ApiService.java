package com.backend.api.ControllerService;

import com.backend.api.auth.AuthController;
import com.backend.api.repoSearchModel.RepoArrayItems;
import com.backend.api.repoSearchModel.RepoResponse;
import com.backend.api.requestModel.FilterModel;
import com.backend.api.requestModel.Search_After_Model;
import com.backend.api.requestModel.Terms;
import com.backend.api.responseModel.UserResponse;
import com.backend.api.searchService.SearchService;
import com.backend.api.responseModel.ResponseModel;
import com.backend.api.searchmodel.ArrayItems;
import com.backend.api.searchmodel.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthController authController;

    @Autowired
    private SearchService searchService;

    private String baseUrl="https://stage.els.scm.mastercard.int:13531";

    public UserResponse getAllObjects(String typeQuery, String keywordQuery, List<FilterModel>filterQuery, int size, int from, List<Double>after)  {




        String url = baseUrl+"/bitbucket-search/_search";



        HttpHeaders headers =authController.generateAuth();

        String req=filterQuery==null?searchService.matchQuery(typeQuery,keywordQuery,size,from,after):searchService.filterFunct(typeQuery,keywordQuery,filterQuery,size,from,after);


        HttpEntity<String> request = new HttpEntity<String>(req,headers);


        ResponseEntity<Response> response = restTemplate.exchange(url, HttpMethod.POST, request, Response.class);

        List<ArrayItems> hits=response.getBody().hits.hits;

        List<ResponseModel> allItems=new ArrayList<>();

        if(hits.size()==0)
        {
            return new UserResponse();
        }


        for(int i=0;i<hits.size();i++)
        {
            ArrayItems curr=hits.get(i);
            int repoId=curr._source.repositoryId;
            int projId=curr._source.projectId;


            ResponseEntity<RepoResponse>repoResponse=searchService.searchRepo(repoId,projId);

            List<RepoArrayItems>repoHits=repoResponse.getBody().hits.hits;

            String projName,repoName;
            if(repoHits.size()==0)
            {
                repoName="NA";
                projName="NA";
            }
            else
            {
                Iterator<RepoArrayItems>repoItr=repoHits.iterator();

                RepoArrayItems currRepo=repoItr.next();

                repoName=currRepo._source.getName();
                projName=currRepo._source.getQuickSearchProjectName();
            }

//            System.out.println(curr.sort);




            ResponseModel responseModel=new ResponseModel(curr._type,curr._source.path,repoName,curr._source.extension,curr._source.filename,curr._source.size,curr._score,projName,curr._source.content,curr.sort);

            allItems.add(responseModel);


        }
//        int size=allItems.size();
//        int size2=hits.size();


//        System.out.println(size);
//        System.out.println(size2);
//        System.out.println();

        UserResponse userResponse=new UserResponse(response.getBody().hits.total.value,allItems);

        return userResponse;

    }
}
