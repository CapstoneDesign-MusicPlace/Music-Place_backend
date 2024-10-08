package org.musicplace.streaming.service;

import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

public class StreamingYouTubeService {

    // 환경변수로부터 유튜브 API 키를 가져옴
    private static final String API_KEY = System.getenv("YOUTUBE_API_KEY");
    private static final String PLAYLIST_API_URL = "https://www.googleapis.com/youtube/v3/playlistItems";

    // 유튜브 재생목록에서 비디오 목록을 가져오는 메서드
    public Map<String, Object> getPlaylist(String playlistId) {
        // 유튜브 API 호출을 위한 URL 생성
        String url = PLAYLIST_API_URL + "?part=snippet&playlistId=" + playlistId + "&key=" + API_KEY;

        // RestTemplate을 사용하여 API 요청
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, Map.class);  // API 응답을 Map 형태로 반환
    }

    // 방장이 새로운 비디오를 유튜브 재생목록에 추가하는 메서드
    public void updatePlaylist(String playlistId, String newVideoId) {
        // POST 요청을 보낼 URL 생성
        String url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&key=" + API_KEY;

        // 요청 본문에 포함될 데이터 (추가할 비디오 정보)
        Map<String, Object> requestBody = new HashMap<>();

        // snippet 객체 생성
        Map<String, Object> snippet = new HashMap<>();
        snippet.put("playlistId", playlistId);  // 재생목록 ID
        snippet.put("resourceId", Map.of("kind", "youtube#video", "videoId", newVideoId));  // 추가할 비디오 정보

        // 최종 요청 본문에 snippet 추가
        requestBody.put("snippet", snippet);

        // RestTemplate을 사용하여 POST 요청을 보내 재생목록에 비디오 추가
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, requestBody, String.class);
    }
}