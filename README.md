ShenronAPI
==========
[![Hex.pm](https://img.shields.io/badge/license-apache2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Platform](https://img.shields.io/badge/platform-scala-green.svg)](http://www.scala-lang.org/)

![Shenron Image][1]

DragonBall characters rest API written in Scala using Play framework. Just a simple pet project to
have some fun. Planning to create another project with the frontend using React Native :muscle:.

Dependency Injection
====================
Used cake pattern to provide dependencies statically (compile time).

Usage
=====
To call the API endpoints you need to provide **encoded credentials**, as the
API has **Basic authentication**. Encode `user:password` using **Base64** to get a string like
the following one: `Basic am9yZ2bU6OdTU5yO12TY0--`. Include it in `Authorization` header
for each call.

To get your own credentials you will need to call the user creation endpoint at
start.

User creation
-------------
* **[POST]** `{insert base url when deployed}/users/create`

Send the following payload:
```json
{
    "username":"Jorge Castillo",
    "password": "123456",
}
```
Create or Update Characters
---------------------------
To create or update a character you must call the following endpoint
* **[POST]** `{insert base url when deployed}/characters/create`

Send the  following payload:
```json
{
    "name":"Goku",
    "description": "Sample description here",
    "photoUrl": "http://images4.fanpop.com/image/photos/16200000/Goku-Super-Saiyan-3-Wallpaper-2-dragonball-z-movie-characters-16255435-1024-768.jpg"
}
```
If the name of the character is already stored in the database, no new character will be created. The found one will get updated with given data.

Find character by id or name
----------------------------
* **[GET]** `{insert base url when deployed}/characters/?id=2`
* **[GET]** `{insert base url when deployed}/characters/?name=goku`

The answer returned will be the details for the wanted character with the following format:
```json
{
  "id": 2,
  "name": "Goku",
  "description": "Sample description here",
  "photoUrl": "http://images4.fanpop.com/image/photos/16200000/Goku-Super-Saiyan-3-Wallpaper-2-dragonball-z-movie-characters-16255435-1024-768.jpg"
}
```

Find all characters
-------------------
* **[GET]** `{insert base url when deployed}/characters/all`

Characters on database will be returned in the following array format:
```json
[
  {
    "id": 2,
    "name": "Goku",
    "description": "Sample description here",
    "photoUrl": "http://images4.fanpop.com/image/photos/16200000/Goku-Super-Saiyan-3-Wallpaper-2-dragonball-z-movie-characters-16255435-1024-768.jpg"
  }
]
```

Delete character
----------------
* **[DELETE]** `{insert base url when deployed}/characters/?id=91`

TODO
----
* Test it. At least public calls.
* Deploy to digital ocean VPS so people can start using it.

Developed By
------------
* Jorge Castillo Pérez - <jorge.castillo.prz@gmail.com>

<a href="https://www.linkedin.com/in/jorgecastilloprz">
  <img alt="Add me to Linkedin" src="https://github.com/JorgeCastilloPrz/EasyMVP/blob/master/art/linkedin.png" />
</a>

License
-------

    Copyright 2016 Jorge Castillo Pérez

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: ./raw/shenron.png