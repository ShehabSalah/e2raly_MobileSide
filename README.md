# e2raly_MobileSide
إe2raly is a hybrid system that serves our Arabic language in different fields. It is a mobile application that performs three main tasks, The first is converting Arabic handwritten into text. The second is detecting plagiarism in Arabic document. Finally, translating our road boards into English. إeraly goals are serving Arabic copyrights, guiding tourists to reach their destinations, and finally solving problems related to documenting handwritten documents.
<br/><br/>
إe2raly is divided into 2  sides:
<br/>
<ol>
<li>Android Mobile Side:</li>
<p>Which responsible on taking an image and convert it to grayscal then black and white. After that the image sent to the server side after converting the image into string to have a better porformance and less mobile data usage while transfaring the image on the internet.</p>
<p>After the image regonized on the server side and the text come back to the mobile side, the user can save his token image with the recognized text to access it later, and the user can also translate the recognized image into english.</p>
<li>IBM BlueMix Server Side:</li>
<p>IBM BlueMix Server side responible on taking the black and white image and start applying some pre-processing on the image like (noise removal and gap filter[to fill the gaps in the image]). The feature Extraction stage come after the pre-processing stage, the system in this stage extract some important features from the the image. After extracting the feature from the image, the system send those features to the Kohonen Neural Networks algorithm and SVM to identify the arabic text and send it back to the mobile side.</p>
<p>You can access the server side code using the following link: </p>
<a href="https://github.com/ShehabSalah/e2raly">e2raly Server Side</a>
</ol>
# Screenshots
<br/>
<img src="https://cloud.githubusercontent.com/assets/16334887/19833917/ac5b61a8-9e53-11e6-8372-7db809411c40.png" width="300px"/> <img src="https://cloud.githubusercontent.com/assets/16334887/19833918/ac5dcb64-9e53-11e6-83b0-3e4de44f6272.png" width="300px"/>
<img src="https://cloud.githubusercontent.com/assets/16334887/19833927/d5654b72-9e53-11e6-9c9f-238ace789873.png" width="300px"/> <img src="https://cloud.githubusercontent.com/assets/16334887/19833926/d5642b48-9e53-11e6-9abb-4448b80ae81d.png" width="300px"/>
<img src="https://cloud.githubusercontent.com/assets/16334887/19833933/135937e0-9e54-11e6-8b37-85376b4489d8.png" width="300px"/> <img src="https://cloud.githubusercontent.com/assets/16334887/19833935/135e3402-9e54-11e6-9588-bb68a0044d7e.png" width="300px"/>
<img src="https://cloud.githubusercontent.com/assets/16334887/19833934/135e2a34-9e54-11e6-828c-14e53083cc0c.png" width="300px"/> <img src="https://cloud.githubusercontent.com/assets/16334887/19833941/7847e5d4-9e54-11e6-8a5b-05614da085e4.png" width="300px"/>
<img src="https://cloud.githubusercontent.com/assets/16334887/19833940/783d98cc-9e54-11e6-8c12-ccdbc42f707e.png" width="300px"/>
