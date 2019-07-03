# Task Master

This is a Code Fellows project. It is a currently a "Task Master" API without a frontend.

## Link to Deployed Site
[My Task Master API](http://stevechutaskmaster.us-west-2.elasticbeanstalk.com/)

## Description
The following are the provided endpoints:
* `GET` request to `/tasks` - get all tasks in JSON format as follows:
  ```
  [
    {
      id: UUID
      title: String
      description: String
      assignee: String
      status: String of either "Available", "Assigned", "Accepted", or "Finished"
    }
  ]
  ```
  
* `GET` request to `/users/{name}/tasks` - get all tasks in JSON format as follows assigned to a specific `assignee`:
  
* `POST`request to `/tasks` - add new task; put data for new entry into the request body in JSON format as follows:
  ```
  {
    title: String
    description: String
    assignee: String
  }
  ```
  * `status` will be set to `Available` if there is no `assignee`
  * `status` will be set to `Assigned` if there is an `assignee`

* `PUT` request to `/tasks/{id}/state` - updates task `status` based on id
  * Changes `status` from `Available` -> `Assigned` -> `Accepted` -> `Finished`
  
* `PUT` request to `/tasks/{id}/assign/{assignee}` - assigns the `assignee` to the given task by `id` and sets the `status` to `Assigned`
  * This API does not do any checks if the current `assignee` has already been assigned or not.
  
* `DELETE` request to `/tasks/{id}` - deletes task by id

## Contributors
* Stephen Chu, [stephenchu530](https://github.com/stephenchu530)

## License
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/stephenchu530/taskmaster/blob/master/LICENSE)
