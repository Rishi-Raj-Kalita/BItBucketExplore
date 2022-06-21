package com.backend.api.ControllerService;

import com.backend.api.auth.AuthController;
import com.backend.api.repoSearchModel.RepoArrayItems;
import com.backend.api.repoSearchModel.RepoResponse;
import com.backend.api.requestModel.FilterModel;
import com.backend.api.responseModel.UserResponse;
import com.backend.api.searchService.SearchService;
import com.backend.api.responseModel.ResponseModel;
import com.backend.api.searchmodel.ArrayItems;
import com.backend.api.searchmodel.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthController authController;

    @Autowired
    private SearchService searchService;

    private int MAXI_REPO=1000;

    private String baseUrl="https://stage.els.scm.mastercard.int:13531";

    public  Map<Integer,RepoArrayItems> mapRepoIds(List<String>queryStrList)
    {
        HashMap<Integer,RepoArrayItems>repoMap=new HashMap<>();

        List<List<RepoArrayItems>>repoHits2=new ArrayList<>();

        for(int i=0;i<queryStrList.size();i++)
        {
            String str=queryStrList.get(i);



            ResponseEntity<RepoResponse>repoResponseResponseEntity2=searchService.searchRepoUtil(str);

            RepoResponse repoResponse2=repoResponseResponseEntity2.getBody();

            repoHits2.add(repoResponse2.hits.hits);
        }


        for(int i=0;i<repoHits2.size();i++)
        {
            List<RepoArrayItems>currRepoList=repoHits2.get(i);


            for(int j=0;j<currRepoList.size();j++)
            {
                RepoArrayItems currRepo=currRepoList.get(j);
                int id=Integer.parseInt(currRepo._id);

                if(repoMap.get(id)==null)
                {
                    repoMap.put(id,currRepo);
                }
            }
        }


        return repoMap;
    }

    public UserResponse getAllObjectsUtilReverse(String typeQuery, String keywordQuery, List<FilterModel>filterQuery, int size, List<Double>after,int next)
    {
        String url = baseUrl+"/bitbucket-search/_search";

        if(size>1000)
        {
            return new UserResponse();
        }

        try{
            HttpHeaders headers =authController.generateAuth();


            String req=filterQuery==null?searchService.matchQueryDesc(typeQuery,keywordQuery,size,after):searchService.filterFunctDesc(typeQuery,keywordQuery,filterQuery,size,after);


            HttpEntity<String> request = new HttpEntity<String>(req,headers);


            ResponseEntity<Response> response = restTemplate.exchange(url, HttpMethod.POST, request, Response.class);


            List<ArrayItems> hits=response.getBody().hits.hits;

            List<ResponseModel> allItems=new ArrayList<>();

            if(hits.size()==0)
            {
                return new UserResponse();
            }

            Set<Integer>repoIds=new HashSet<>();

            for(int i=0;i<hits.size();i++)
            {
                ArrayItems curr=hits.get(i);

                int repoId=curr._source.repositoryId;
                repoIds.add(repoId);
            }


            List<String> queryStrList=searchService.matchReposQuery(repoIds);

            Map<Integer,RepoArrayItems> repoMap=mapRepoIds(queryStrList);


            for(int i=0;i<hits.size();i++)
            {

                ArrayItems curr=hits.get(i);

                int repoId=curr._source.repositoryId;

                String repoName="Na",projName="Na";


                if(repoMap.get(repoId)!=null)
                {
                    RepoArrayItems currRepo=repoMap.get(repoId);

                    repoName=currRepo._source.getName();
                    projName=currRepo._source.getQuickSearchProjectName();
                }

                ResponseModel responseModel=new ResponseModel(curr._type,curr._source.path,repoName,curr._source.extension,curr._source.filename,curr._source.size,curr._score,projName,curr._source.content,curr.sort);

                allItems.add(responseModel);


            }

            Collections.reverse(allItems);

            UserResponse userResponse=new UserResponse(response.getBody().hits.total.value,allItems);

            return userResponse;

        }catch(Exception error)
        {
            System.out.println(error);
        }

        return new UserResponse();




    }

    public UserResponse getAllObjectsUtil(String typeQuery, String keywordQuery, List<FilterModel>filterQuery, int size, List<Double>after,int next)
    {
        String url = baseUrl+"/bitbucket-search/_search";

        if(size>1000)
        {
            return new UserResponse();
        }

//        System.out.println("next"+next);

        if(next==1)
        {
          try{
              return getAllObjectsUtilReverse(typeQuery,keywordQuery,filterQuery,size,after,next);
          }catch(Exception e)
          {
              System.out.println(e);

              return new UserResponse();
          }
        }

        try{
                HttpHeaders headers =authController.generateAuth();


                String req=filterQuery==null?searchService.matchQuery(typeQuery,keywordQuery,size,after):searchService.filterFunct(typeQuery,keywordQuery,filterQuery,size,after);


                HttpEntity<String> request = new HttpEntity<String>(req,headers);


                ResponseEntity<Response> response = restTemplate.exchange(url, HttpMethod.POST, request, Response.class);


                List<ArrayItems> hits=response.getBody().hits.hits;

                List<ResponseModel> allItems=new ArrayList<>();

                if(hits.size()==0)
                {
                    return new UserResponse();
                }

                Set<Integer>repoIds=new HashSet<>();

                for(int i=0;i<hits.size();i++)
                {
                    ArrayItems curr=hits.get(i);

                    int repoId=curr._source.repositoryId;
                    repoIds.add(repoId);
                }


                List<String> queryStrList=searchService.matchReposQuery(repoIds);

                Map<Integer,RepoArrayItems> repoMap=mapRepoIds(queryStrList);


                for(int i=0;i<hits.size();i++)
                {

                    ArrayItems curr=hits.get(i);

                    int repoId=curr._source.repositoryId;

                    String repoName="Na",projName="Na";


                    if(repoMap.get(repoId)!=null)
                    {
                        RepoArrayItems currRepo=repoMap.get(repoId);

                        repoName=currRepo._source.getName();
                        projName=currRepo._source.getQuickSearchProjectName();
                    }

                    ResponseModel responseModel=new ResponseModel(curr._type,curr._source.path,repoName,curr._source.extension,curr._source.filename,curr._source.size,curr._score,projName,curr._source.content,curr.sort);

                    allItems.add(responseModel);


                }

                UserResponse userResponse=new UserResponse(response.getBody().hits.total.value,allItems);

                return userResponse;

        }catch(Exception error)
        {
            System.out.println(error);
        }

        return new UserResponse();




    }


}
