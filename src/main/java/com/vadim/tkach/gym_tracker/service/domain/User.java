package com.vadim.tkach.gym_tracker.service.domain;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
        private  UUID id;
        private  String name;
        private  String email;
        private  String password;
}
