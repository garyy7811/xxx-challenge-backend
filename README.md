xxx Backend Challenge
============================

## Intro
This is a solution to an interview code challenge which is designed to see how candidate work
on a back-end project. I spent a little more than 2 hours, the Windows/Linux compatible issue took more time than expected.

Even my solution was not accepted, I still think it's worth sharing, from my perspective, my solution:
- Reuse Spring Integration component to minimize code.
- Highly extendable, can easily add other features( retry, caching etc. ) with AOP/Advice.
- Prepared for integrating with more sophisticated system like Spring Cloud, Docker.


### Requirements - PART 1

- Create a GIF search web service
- It should have an HTTP GET API with the path `/search/[search term]`
- It should run on port 8080
- Leverage the [Giphy API](https://github.com/giphy/GiphyAPI) and use the Giphy public beta key
- Always return exactly 5 results or 0 results if there are less than 5 available
- Responses should be JSON in the following format:

```json
{
  "data": [
    {
      "gif_id": "FiGiRei2ICzzG",
      "url": "http://giphy.com/gifs/funny-cat-FiGiRei2ICzzG"
    }
  ]
}
```

### Requirements - PART 2
- Complete the install.sh and start.sh scripts
- install.sh should do all the preparation for your project to run, such as downloading any dependencies and compiling if necessary
- start.sh should start up your service
- After we run start.sh, your service should be able to repond to API requests
- These scripts should be able to run on any machine as long as they are Ubuntu 14+
