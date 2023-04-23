package com.example.greencoding.onlinegame;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {

    @PostMapping("/onlinegame/calculate")
    public ResponseEntity<List<List<Clan>>> getCalculatedEntranceOrder(@RequestBody GameRequest gameRequest) {
        return new ResponseEntity<>(null);
    }

}
