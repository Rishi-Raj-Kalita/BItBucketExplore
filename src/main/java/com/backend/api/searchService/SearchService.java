package com.backend.api.searchService;

import com.backend.api.auth.AuthController;
import com.backend.api.repoSearchModel.RepoResponse;
import com.backend.api.requestModel.FilterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SearchService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthController authController;

    private String baseUrl="https://stage.els.scm.mastercard.int:13531";

    public List<String> matchReposQuery(Set<Integer> repoIds)
    {
        String left="{\"query\":{\"bool\":{\"should\":[";
        String right="],\"minimum_should_match\":1,\"boost\":1}}}";
        String tmpLeft="{\"query\":{\"bool\":{\"should\":[";

        int i=0;

        int cnt=0;

        List<String>queryStrings=new ArrayList<>();

        for(Integer id:repoIds)
        {
            String leftQuery="{\"term\":{\"_id\":";
            String rightQuery="}}";



            if(cnt==9)
            {
                left+=right;
                queryStrings.add(left);
                left=tmpLeft;
            }

            String tmp=String.valueOf(id);

            String tmpQuery=leftQuery+tmp+rightQuery;
            left+=tmpQuery;

            if(i!=repoIds.size()-1&&cnt!=8)
            {
                left+=',';
            }
            cnt=(cnt+1)%10;
            i++;
        }

        left+=right;



        queryStrings.add(left);

        System.out.println("repo string "+queryStrings.get(0));


        return queryStrings;


    }

    public String matchQueryDesc(String typeQuery, String keywordQuery, int sizeQuery, List<Double>after)
    {
        String type='"'+typeQuery+'"';
        String keyword='"'+keywordQuery+'"';
        String cnt=String.valueOf(sizeQuery);

        String req;

        if(after==null||after.size()==0)
        {
            req="{\"size\":"+cnt+",\"query\":{\"match\":{"+type+":"+keyword+"}},\"sort\":[{\"_score\":\"asc\"},{\"size\":\"desc\"},{\"repositoryId\":\"desc\"},{\"projectId\":\"desc\"}]}";
        }
        else
        {
            req="{\"size\":"+cnt+",\"query\":{\"match\":{"+type+":"+keyword+"}},\"sort\":[{\"_score\":\"asc\"},{\"size\":\"desc\"},{\"repositoryId\":\"desc\"},{\"projectId\":\"desc\"}],\"search_after\":"+after+"}";
        }



        System.out.println("Request"+req);

        return req;
    }

    public String filterFunctDesc(String typeQuery, String keywordQuery, List<FilterModel> filterQuery,int sizeQuery,List<Double>after)
    {
        String req="";
        String type1,key1,type2,key2,type3,key3;
        String type='"'+typeQuery+'"';
        String keyword='"'+keywordQuery+'"';
        String cnt=String.valueOf(sizeQuery);



        if(filterQuery.size()==1)
        {
            type1='"'+filterQuery.get(0).type+'"';
            key1='"'+filterQuery.get(0).keyword+'"';

            if(after.size()==0||after==null)
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}}]}},\"sort\":[{\"_score\":\"asc\"},{\"size\":\"desc\"},{\"repositoryId\":\"desc\"},{\"projectId\":\"desc\"}]}";
            }
            else
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}}]}},\"sort\":[{\"_score\":\"asc\"},{\"size\":\"desc\"},{\"repositoryId\":\"desc\"},{\"projectId\":\"desc\"}],\"search_after\":"+after+"}";
            }



        }
        else if(filterQuery.size()==2)
        {
            type1='"'+filterQuery.get(0).type+'"';
            key1='"'+filterQuery.get(0).keyword+'"';
            type2='"'+filterQuery.get(1).type+'"';
            key2='"'+filterQuery.get(1).keyword+'"';

            if(after.size()==0||after==null)
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}},{\"match\":{"+type2+":"+key2+"}}]}},\"sort\":[{\"_score\":\"asc\"},{\"size\":\"desc\"},{\"repositoryId\":\"desc\"},{\"projectId\":\"desc\"}]}";
            }
            else
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}},{\"match\":{"+type2+":"+key2+"}}]}},\"sort\":[{\"_score\":\"asc\"},{\"size\":\"desc\"},{\"repositoryId\":\"desc\"},{\"projectId\":\"desc\"}],\"search_after\":"+after+"}";
            }
        }
        else
        {
            type1='"'+filterQuery.get(0).type+'"';
            key1='"'+filterQuery.get(0).keyword+'"';
            type2='"'+filterQuery.get(1).type+'"';
            key2='"'+filterQuery.get(1).keyword+'"';
            type3='"'+filterQuery.get(2).type+'"';
            key3='"'+filterQuery.get(2).keyword+'"';

            if(after.size()==0||after==null)
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}},{\"match\":{"+type2+":"+key2+"}},{\"match\":{"+type3+":"+key3+"}}]}},\"sort\":[{\"_score\":\"asc\"},{\"size\":\"desc\"},{\"repositoryId\":\"desc\"},{\"projectId\":\"desc\"}]}";
            }
            else
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}},{\"match\":{"+type2+":"+key2+"}},{\"match\":{"+type3+":"+key3+"}}]}},\"sort\":[{\"_score\":\"asc\"},{\"size\":\"desc\"},{\"repositoryId\":\"desc\"},{\"projectId\":\"desc\"}],\"search_after\":"+after+"}";
            }


        }

        System.out.println("Request"+req);


        return req;
    }


    public String matchQuery(String typeQuery, String keywordQuery, int sizeQuery, List<Double>after)
    {
        String type='"'+typeQuery+'"';
        String keyword='"'+keywordQuery+'"';
        String cnt=String.valueOf(sizeQuery);

        String req;

        if(after==null||after.size()==0)
        {
            req="{\"size\":"+cnt+",\"query\":{\"match\":{"+type+":"+keyword+"}},\"sort\":[{\"_score\":\"desc\"},{\"size\":\"asc\"},{\"repositoryId\":\"asc\"},{\"projectId\":\"asc\"}]}";
        }
        else
        {
            req="{\"size\":"+cnt+",\"query\":{\"match\":{"+type+":"+keyword+"}},\"sort\":[{\"_score\":\"desc\"},{\"size\":\"asc\"},{\"repositoryId\":\"asc\"},{\"projectId\":\"asc\"}],\"search_after\":"+after+"}";
        }



        System.out.println("Request"+req);

        return req;
    }

    public String filterFunct(String typeQuery, String keywordQuery, List<FilterModel> filterQuery,int sizeQuery,List<Double>after)
    {
        String req="";
        String type1,key1,type2,key2,type3,key3;
        String type='"'+typeQuery+'"';
        String keyword='"'+keywordQuery+'"';
        String cnt=String.valueOf(sizeQuery);



        if(filterQuery.size()==1)
        {
            type1='"'+filterQuery.get(0).type+'"';
            key1='"'+filterQuery.get(0).keyword+'"';

            if(after.size()==0||after==null)
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}}]}},\"sort\":[{\"_score\":\"desc\"},{\"size\":\"asc\"},{\"repositoryId\":\"asc\"},{\"projectId\":\"asc\"}]}";
            }
            else
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}}]}},\"sort\":[{\"_score\":\"desc\"},{\"size\":\"asc\"},{\"repositoryId\":\"asc\"},{\"projectId\":\"asc\"}],\"search_after\":"+after+"}";
            }



        }
        else if(filterQuery.size()==2)
        {
            type1='"'+filterQuery.get(0).type+'"';
            key1='"'+filterQuery.get(0).keyword+'"';
            type2='"'+filterQuery.get(1).type+'"';
            key2='"'+filterQuery.get(1).keyword+'"';

            if(after.size()==0||after==null)
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}},{\"match\":{"+type2+":"+key2+"}}]}},\"sort\":[{\"_score\":\"desc\"},{\"size\":\"asc\"},{\"repositoryId\":\"asc\"},{\"projectId\":\"asc\"}]}";
            }
            else
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}},{\"match\":{"+type2+":"+key2+"}}]}},\"sort\":[{\"_score\":\"desc\"},{\"size\":\"asc\"},{\"repositoryId\":\"asc\"},{\"projectId\":\"asc\"}],\"search_after\":"+after+"}";
            }
        }
        else
        {
            type1='"'+filterQuery.get(0).type+'"';
            key1='"'+filterQuery.get(0).keyword+'"';
            type2='"'+filterQuery.get(1).type+'"';
            key2='"'+filterQuery.get(1).keyword+'"';
            type3='"'+filterQuery.get(2).type+'"';
            key3='"'+filterQuery.get(2).keyword+'"';

            if(after.size()==0||after==null)
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}},{\"match\":{"+type2+":"+key2+"}},{\"match\":{"+type3+":"+key3+"}}]}},\"sort\":[{\"_score\":\"desc\"},{\"size\":\"asc\"},{\"repositoryId\":\"asc\"},{\"projectId\":\"asc\"}]}";
            }
            else
            {
                req="{\"size\":"+cnt+",\"query\":{\"bool\":{\"must\":[{\"match\":{"+type+":"+keyword+"}},{\"match\":{"+type1+":"+key1+"}},{\"match\":{"+type2+":"+key2+"}},{\"match\":{"+type3+":"+key3+"}}]}},\"sort\":[{\"_score\":\"desc\"},{\"size\":\"asc\"},{\"repositoryId\":\"asc\"},{\"projectId\":\"asc\"}],\"search_after\":"+after+"}";
            }


        }

        System.out.println("Request"+req);


        return req;
    }

    public ResponseEntity<RepoResponse> searchRepoUtil(String req)
    {
        String url=baseUrl+"/bitbucket-repository/_search";

        HttpHeaders headers=authController.generateAuth();

        HttpEntity<String> request=new HttpEntity<>(req,headers);

        ResponseEntity<RepoResponse>response=restTemplate.exchange(url, HttpMethod.POST,request,RepoResponse.class);


        return response;
    }

}
