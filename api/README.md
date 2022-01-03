# API Routes

> By default, Einsen runs on port 8000. You can change this from `application.conf` file inside `src/main/resources`.
> You'll need to install [Postgresql](https://www.postgresql.org/download/).
> While installing, please check the `PgAdmin` option to install it too. It's a web-based GUI tool used to interact with the Postgres database.
>
> PS: Please keep the username and password you create while installing Postgresql. You'll
> need them to connect Einsen Server to Postgresql (locally)

#### Skip to

- [Registering a new user](#h1)
- [To login an existing user](#h2)
- [To create a new task](#h3)
- [To retrieve all the task of the given user](#h4)
- [To update a task](#h5)
- [To delete a task](#h6)

## <a name="h1">1. To Register a new user (POST Request)</a>

###### `/api/auth/register`

Body

```json
{
    "username": "your_username",
    "password": "your_password"
}
```

Rules

```
---------
Username and Password field should not be blank.
Username and Password length should be between 4 to 10 characters.
Username should not only consists of numbers.
Special characters are not allowed inside username.
--------

This rules are verified on Server side.
```

Response

```json
{
    "success": true,
    "additionalMessage": "Account created successfully.",
    "data": null
}

```

## <a name="h2">2. To Login an existing user (POST Request)</a>

###### `/api/auth/login`

Body

```json
{
    "username": "your_username",
    "password": "your_password"
}
```

Rules

```
---------
Username and Password field should not be blank.
--------

This rules are verified on Server side.
```

Success Response

```json
{
    "success": true,
    "additionalMessage": null,
    "data": {
        "userId": "user_id",
        "token": "jwt_token"
    }
}

```

> Except for login and register routes, every other routes requires you
> to attach Bearer token of the user retrieved after Login in this format:

Header Key  | Header Value
------------- | -------------
`Authorization`   | `Bearer <User Token>`

## <a name="h3">3. To create a task (POST Request)</a>

###### `/api/task/create`

Body

```json
{
    "title": "task_title",
    "description": "task_description",
    "category": "category_name",
    "emoji": "x",
    "urgency": 0,
    "importance": 0,
    "priority": 0,
    "due": "due_date",
    "isCompleted": false
}
```

Rules

```
---------
All fields are mandatory, leaving out any one of them will throw 500 Server Error.
Title, description and category field should be of 4 characters or more.
--------

This rules are verified on Server side.
```

Success Response

```json
{
    "success": true,
    "additionalMessage": null,
    "data": {
        "taskID": "newly_created_task_id"
    }
}
```

## <a name="h4">4. To retrieve all the task (GET Request)</a>

###### `/api/task/get?userId={id}&category={category_name}`

Parameter  | Parameter Description
------------- | -------------
`userId`   | `To fetch the list of task of the given user ID`
`category` | `If is empty or null, all tasks regardless of their category would get displayed.`

Success Response

```json
{
    "success": true,
    "additionalMessage": null,
    "data": [
        {
            "id": "d8d7c578-b8af-459c-865c-3ff518a2b630",
            "title": "updated_task_title",
            "description": "updated_task_description",
            "category": "updated_category_name",
            "emoji": "y",
            "urgency": 1,
            "importance": 1,
            "priority": 1,
            "due": "updated_due_date",
            "isCompleted": true,
            "created": 1638331650467,
            "updated": 1638331650553
        },
        {
            "id": "d8d7c578-b8af-459c-865c-3ff518a2b630",
            "title": "updated_task_title",
            "description": "updated_task_description",
            "category": "updated_category_name",
            "emoji": "y",
            "urgency": 1,
            "importance": 1,
            "priority": 1,
            "due": "updated_due_date",
            "isCompleted": true,
            "created": 1638331650467,
            "updated": 1638331650553
        }
    ]
}
```

## <a name="h5">5. To update a task (PUT Request)</a>

###### `/api/task/update?taskId={id}`

Parameter  | Parameter Description
------------- | -------------
`taskId`   | `Task ID which you wish to update`

Body

```json
{
    "title": "updated_task_title",
    "description": "updated_task_description",
    "category": "updated_category_name",
    "emoji": "y",
    "urgency": 1,
    "importance": 1,
    "priority": 1,
    "due": "updated_due_date",
    "isCompleted": true
}
```

Rules

```
---------
All fields are mandatory, leaving out any one of them will throw 500 Server Error.
Title, description and category field should be of 4 characters or more.
Task should be of user.
--------

This rules are verified on Server side.
```

Success Response

```json
{
    "success": true,
    "additionalMessage": null,
    "data": {
        "taskID": "task_id"
    }
}
```

## <a name="h6">6. To delete a task (DELETE Request)</a>

###### `/api/task/delete?taskId={id}`

Parameter  | Parameter Description
------------- | -------------
`taskId`   | `Task ID which you wish to delete`

Rules

```
---------
Task should be of user.
--------

This rules are verified on Server side.
```

Success Response

```json
{
    "success": true,
    "additionalMessage": null,
    "data": {
        "taskID": "Success"
    }
}
```

### TODO:

- [ ] Add `orderBy` parameter on _get all task_ request.
- [ ] Verify if response is empty when creating and updating a task (
  except `title, description & category` as they are already been verified).
