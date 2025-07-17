package com.otus.java.advanced.controller;

import com.otus.java.advanced.domain.ai.Teacher;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.util.List;

@ApplicationScoped
@Produces("application/json")
@RegisterForReflection
@Path("/teacher")
public class AITeacher {

    @Inject
    private Teacher teacher;

    @GET
    @Path("/plan/{lang}")
    public String planGenerator (String lang) {
        return teacher.lessonPlan(lang);
    }

    @POST
    @Path("/answer/{lang}")
    public List<String> answer(String lang, String question) {
        return teacher.answer(lang, question)
                .content();
    }

    @POST
    @Path("/answer/course/java-advanced")
    public List<String> answerJavaAdvanced(String question) {
        return teacher.answerJavaAdvanced(question)
                .content();
    }
}
