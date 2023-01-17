<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">

<h3 align="center">Concert_Dates</h3>

  <p align="center">
    Finds your favourite artists on Spotify and lets you search for their concert dates.
    <br />
    <a href="https://github.com/RidwaanT/Concert_Dates/blob/master/Concert%20Dates%20Application%20Info%20Sheet.pdf"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/RidwaanT/Concert_Dates">View Demo</a>
    ·
    <a href="mailto:ridwaantoure@gmail.com">Report Bug</a>
    ·
    <a href="mailto:ridwaantoure@gmail.com">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project


A project completed for my COSC 3P97 course. This project uses APIs from Google, TicketMaster and Spotify to allow Spotify users to find their favourite artists and then search for concert dates based on the closest locations

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* [![AndroidStudio][AndroidStudio]][AndroidStudio-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started


### Prerequisites


* Install <a href="https://developer.android.com/studio/">Android Studio</a> on your machine.

* Within Android Studio download an emulator with API 23 or higher, preferably Pixel 3a (The Pixel 6 works but can be very slow)

### Installation


#### Ticketmaster API Key

The project utilizes the  <a href="https://developer.ticketmaster.com/api-explorer/v2/"> Ticketmaster api </a> to determine the location of the concerts for the artists found. 

1. By <a href="https://developer-acct.ticketmaster.com/user/register"> creating a developer account with Ticketmaster</a> you can create your own API key. 

2. Once the key is created open the directory `~/.gradle/gradle.properties` and write the following

```yaml
tm_api_key="<YOUR_API_KEY>"
```

#### Google Maps API Key

The next API Key that is used is the Google Maps API Key. 

3. Use the <a href="https://developers.google.com/maps/documentation/android-sdk/get-api-key">instructions on Google Maps Platform to get API Key</a>
4. Create a file called `google_maps_api.xml` in the app/src/debug/res/values directory. Then add this to the file.

```xml
<resources>
  <!--
    Once you have your key (it starts with "AIza"), replace the "google_maps_key"
    string in this file.
    -->
    <string name="google_maps_key" translatable="false" templateMergeStrategy="preserve">YOUR_MAPS_API_KEY</string>
</resources>
```
5. Then go to the `AndroidManifest.xml` and place the following inside the `<application>` tag:
```xml
<meta-data
  android:name="com.google.android.geo.API_KEY"
  android:value="@string/google_maps_key">
```

#### Spotify Account

6. A Spotify account is needed to be able to access the information on the top 20 artists. A Spotify account can be created for free here <a href="https://www.spotify.com/us/signup">here</a>

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage
1. Launch the app 
2. Login to your Spotify account (needs to have some usage).
3. Select your favourite artist.
4. Select a Concert Date from the list.
5. You are provided with a map and a seat map of the concert.

### Screenshots

<img src="/images/Spotify_Login.png" width=200> <img src="/images/Top_Artists_Page.png" width=200> <img src="/images/Artist_Page_Permissions.png" width=200> <img src="/images/Artist_Page.png" width=200> <img src="/images/Concert_Page.png" width=200>

_For more examples, please refer to the [Documentation](https://github.com/RidwaanT/Concert_Dates/blob/master/Concert%20Dates%20Application%20Info%20Sheet.pdf)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [ ] Change Radius for concerts
- [ ] Find Concerts by Songs
- [ ] Book Tickets

See the [open issues](https://github.com/RidwaanT/Concert_Dates/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Ridwaan Toure - ridwaantoure@gmail.com

Project Link: [https://github.com/RidwaanT/Concert_Dates](https://github.com/RidwaanT/Concert_Dates)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* []()
* []()
* []()

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/RidwaanT/Concert_Dates.svg?style=for-the-badge
[contributors-url]: https://github.com/RidwaanT/Concert_Dates/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/RidwaanT/Concert_Dates.svg?style=for-the-badge
[forks-url]: https://github.com/RidwaanT/Concert_Dates/network/members
[stars-shield]: https://img.shields.io/github/stars/RidwaanT/Concert_Dates.svg?style=for-the-badge
[stars-url]: https://github.com/RidwaanT/Concert_Dates/stargazers
[issues-shield]: https://img.shields.io/github/issues/RidwaanT/Concert_Dates.svg?style=for-the-badge
[issues-url]: https://github.com/RidwaanT/Concert_Dates/issues
[license-shield]: https://img.shields.io/github/license/RidwaanT/Concert_Dates.svg?style=for-the-badge
[license-url]: https://github.com/RidwaanT/Concert_Dates/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/ridwaan-toure
[product-screenshot]: images/screenshot.png
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
[Vue-url]: https://vuejs.org/
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/
[Svelte.dev]: https://img.shields.io/badge/Svelte-4A4A55?style=for-the-badge&logo=svelte&logoColor=FF3E00
[Svelte-url]: https://svelte.dev/
[Laravel.com]: https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white
[Laravel-url]: https://laravel.com
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[JQuery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[JQuery-url]: https://jquery.com 
[AndroidStudio]: https://img.shields.io/badge/Android%20Studio-brightgreen?style=for-the-badge&logo=AndroidStudio&logoColor=white
[AndroidStudio-url]: https://developer.android.com/studio
