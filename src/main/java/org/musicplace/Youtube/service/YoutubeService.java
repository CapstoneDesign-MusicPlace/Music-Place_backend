package org.musicplace.Youtube.service;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.musicplace.Youtube.dto.YoutubeVidioDto;
import org.musicplace.global.exception.ErrorCode;
import org.musicplace.global.exception.ExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class YoutubeService {

    //@Value 어노테이션을 사용하여 application.yml에서 정의한 YouTube API 키를 주입 받음
    @Value("${youtube.api.key}")
    private String apiKey;

    public List<YoutubeVidioDto> searchVideo(String query) throws IOException {
        // JSON 데이터를 처리하기 위한 JsonFactory 객체 생성
        JsonFactory jsonFactory = new JacksonFactory();

        // YouTube 객체를 빌드하여 API에 접근할 수 있는 YouTube 클라이언트 생성
        YouTube youtube = new YouTube.Builder(
                new com.google.api.client.http.javanet.NetHttpTransport(),
                jsonFactory,
                request -> {})
                .build();

        // YouTube Search API를 사용하여 동영상 검색을 위한 요청 객체 생성
        YouTube.Search.List search = youtube.search().list(Collections.singletonList("id,snippet"));

        // API 키 설정
        search.setKey(apiKey);

        // 검색어 설정
        search.setQ(query);

        // 검색 요청 실행 및 응답 받아오기
        SearchListResponse searchResponse = search.execute();

        // 검색 결과에서 동영상 목록 가져오기
        List<SearchResult> searchResultList = searchResponse.getItems();

        if (searchResultList != null) {
            //검색 결과 중 첫 번째 동영상 정보 가져오기
            List<YoutubeVidioDto> filteringResultList = searchResultList
                    .stream()
                    .map(searchResult -> YoutubeVidioDto.builder()
                                    .vidioId(searchResult.getId().getVideoId())
                                    .vidioTitle(searchResult.getSnippet().getTitle())
                                    .build()
                            )
                    .collect(Collectors.toList());

            return filteringResultList;
        }
        throw new ExceptionHandler(ErrorCode.NOT_FOUND_RESULT);
    }
}