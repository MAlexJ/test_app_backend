package com.malex.test_app_backend.controller.webapp.quest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "X-Auth-Token-Auth")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/webapp/quests")
public class QuestApiController {}
