package com.daypaytechnologies.digichitfund.infrastructure;

public class Response {

    public Long id;

    private Response() {

    }

    public Response(Long id) {
        this.id = id;
    }

    public static Response of(Long id) {
        return new Response(id);
    }

    public static Response empty() {
        return new Response(0L);
    }

    public Long getId() {
        return id;
    }
}
