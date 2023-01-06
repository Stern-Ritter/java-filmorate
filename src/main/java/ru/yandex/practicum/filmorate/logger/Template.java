package ru.yandex.practicum.filmorate.logger;

public class Template {
    public static final String POST_FILM_TEMPLATE = "POST '/films', parameters={%s}";
    public static final String PUT_FILM_TEMPLATE = "PUT '/films', parameters={%s}";
    public static final String GET_FILM_BY_ID_TEMPLATE = "GET '/films/%s'.";
    public static final String GET_FILMS_TEMPLATE = "GET '/films'.";
    public static final String GET_POPULAR_FILMS_TEMPLATE = "GET '/films/popular?count=%s'";
    public static final String PUT_FILM_LIKE_TEMPLATE ="PUT '/films/%s/like/%s'";
    public static final String DELETE_FILM_LIKE_TEMPLATE = "DELETE '/films/%s/like/%s'";

    public static final String POST_USER_TEMPLATE = "POST '/users', parameters={%s}";
    public static final String PUT_USER_TEMPLATE = "PUT '/users', parameters={%s}";
    public static final String GET_USER_BY_ID_TEMPLATE = "GET '/users/%s'";
    public static final String GET_USERS_TEMPLATE = "GET '/users'";
    public static final String PUT_USER_FRIEND_TEMPLATE = "PUT '/users/%s/friends/%s'";
    public static final String DELETE_USER_FRIEND_TEMPLATE = "DELETE '/users/%s/friends/%s'";
    public static final String GET_USER_FRIENDS_TEMPLATE = "GET '/%s/friends/'";
    public static final String GET_USER_COMMON_FRIENDS_TEMPLATE = "GET '/users/%s/friends/common/%s'";
}
