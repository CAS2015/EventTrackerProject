# What's in the Box? (EventTrackerProject)


## Overview
This project is a dynamic full-stack web application for a smart label system.
Gone are the days where a user must rifle through boxes in an attic to try and
find a specific item or where a user must try to take meticulous notes when
moving. These labels have QR codes that can be printed and placed on the box in
addition to the normal label content. The QR code connects the box to a
database where the user can take pictures of the contents of the box as well as
take notes as to what's inside the box, what room the contents go to, and
whether it is fragile or not.

The user can manage their account, have multiple location groups (storage unit,
attic, garage, etc.), and have boxes that can be sorted by room or searched by
keyword. Users are able to view, create, update, and delete boxes and locations
as needed.


### Skills/Technologies Used
- Languages: Java / JEE
- Web: JSON
- Database: SQL, JPA, JDBC, JPQL
- APIs: RESTful
- Methodologies: TDD
- Frameworks: Spring Boot
- Configuration Management: Git


## How to Run
- Link: http://3.13.22.174:8080/WhatsInTheBox
- username: TBD with front-end completion
- password: TBD with front-end completion


## REST API

| HTTP Method | Resource URI               | Request Body | Returns              | Functionality     |
|-------------|----------------------------|--------------|----------------------|-------------------|
| GET         | `api/users`                |              | List&lt;User&gt;     | Lists all users   |
| GET         | `api/users/{id}`           |              | User                 | List single user |
| POST        | `api/users`               | User         | User                 | Creates new user |
| PUT         | `api/users/{id}`           | User         | User                 | Updates user     |
| DELETE      | `api/users/{id}`           |              |                      | Deactivates user   |
| GET         | `api/users/{id}/locations` |              | List&lt;Location&gt; | Lists all locations for user   |
| GET         | `api/users/{id}/locations/{locId}` |      | Location             | Lists single location for user   |
| POST        | `api/users/{id}/locations` | Location     | Location             | Creates new location |
| PUT         | `api/users/{id}/locations/{locId}`| Location | Location          | Updates location     |
| DELETE      | `api/users/{id}/locations/{locId}`|        |                     | Deactivates location  |
| GET         | `api/users/{id}/locations/{locId}/boxes` |   | List&lt;Box&gt;  | Lists all boxes for user location  |
| GET         | `api/users/{id}/locations/{locId}/boxes/filter/{roomName}` |   | List&lt;Box&gt;  | Lists all boxes from specified room for user location  |
| GET         | `api/users/{id}/locations/{locId}/boxes/search/{keyword}` |   | List&lt;Box&gt;  | Lists all boxes with name or contents containing keyword for user location  |
| GET         | `api/users/{id}/locations/{locId}/boxes/{boxId}` |      | Box   | Lists single box for user location |
| POST        | `api/users/{id}/locations/{locId}/boxes` | Box     | Box             | Creates new box |
| PUT         | `api/users/{id}/locations/{locId}/boxes/{boxId}`| Box | Box          | Updates box     |
| DELETE      | `api/users/{id}/locations/{locId}/boxes/{boxId}`|        |                     | Deactivates box  |


## Database Schema
![image](https://user-images.githubusercontent.com/12469178/113540569-a674ab00-959d-11eb-9e6e-1725c66870cb.png)
