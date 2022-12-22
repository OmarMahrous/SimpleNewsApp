
# News Application

App is built for displaying news based on specific country and category.
Enable users to find news of specific country & category.
Users can save favourite news to read later.
On tapping on an article, Open the article in browser.



## Authors

- [@OmarMahrous](https://www.github.com/OmarMahrous)


## API Reference

#### Get articles

```https://newsapi.org/v2/
  GET /top-headlines
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key |
| `category` | `string` |
| `country` | `string` |
| `q` | `string` |

#### Get sources

```http
  GET /top-headlines/sources/${apiKey}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `apiKey`      | `string` | **Required**. apiKey |

#### getSources(apiKey)

Takes apiKey and returns list of sources.

#### getArticles(category, country, 
q, apiKey)

Takes category, country, 
q (query for search), apiKey and returns list of articles.

## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

`API_KEY` = "b939b2761cf748ea89ebe6ba1e37da0b"




## Demo

link to demo or APK

https://drive.google.com/file/d/1_D1N6nyg13Q4SPnEYLynTOChfs3NdaXH/view?usp=share_link
## Features

- Show countries & categories
- Show articles
- Search articles
- Open the article in browser
- Save favourite articles


## Feedback

If you have any feedback, please reach out to us at Mahrous832@gmail.com


## 🚀 About Me
I worked on a lot of projects alone from A to Z and I solved many problems and learned many features although I am young and I am fresh graduated and I have 3 years of experience.

My strong skills:-
1 - Excellent Searcher and problem solver
2 - Advanced testing and debugging skills
3 - Attention to detail and continuous learning


## 🛠 Skills
OOP, 
Solid principles,
Restful APIs,
Real-time (Socket) API, 
Publishing Apps on the store, 
Multiple modules, 
Localization,
Data structures & Algorithms
, Android architecture,
 Material design (Animations),
 Testing and debugging,
Git version control.


## Appendix

Any additional information goes here

Selected software design pattern
is MVVM

MVVM architecture is a Model-View-ViewModel architecture that removes the tight coupling between each component. 
Most importantly, in this architecture, the children don't have the direct reference to the parent, they only have the reference by observables.
## Roadmap

- Additional browser support

- Add more integrations

Open android project from Github

1 - Open your Android Studio then go to the File > New > Project from Version Control 

2 - After clicking on the Project from Version Control a pop-up screen will arise like below. In the Version control choose Git from the drop-down menu. 

3 - Then at last paste the link ("https://github.com/OmarMahrous/SimpleNewsApp.git") in the URL and choose your Directory. Click on the Clone button and you are done.