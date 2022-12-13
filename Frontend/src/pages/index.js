import BaseClass from "../util/baseClass.js";
import DataStore from "../util/DataStore.js";
import ExampleClient from "../api/exampleClient.js";
import ReviewClient from "../api/reviewClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class IndexPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate', 'renderExample'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('search-by-teacher-form').addEventListener('submit', this.onGetByTeacher);
        document.getElementById('search-by-course-form').addEventListener('submit', this.onGetByCourse);
        document.getElementById('create-review-form').addEventListener('submit', this.onCreateReview);
        this.client = new ReviewClient();

        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderReviews() {
        let resultArea = document.getElementById("result-info");

        const reviews = this.dataStore.get("reviews");
        let html = '';
        if (reviews.length > 0) {
            for (let review of reviews) {
                html += `
                <div class="review-container">
                <div class="review-info">
                    <div class="review-info-top-container">
                        <div class="total-rating-container"><span class="total-rating">${review.totalRating}</span></div>
                        <div class="course-title-container"><span class="course-title">Course title: ${review.courseTitle}</span></div>
                        <div class="teacher-name-container"><span class="teacher-name">Teacher name: ${review.teacherName}</span></div>
                    </div>
                    <div class="review-info-mid-container">
                        <div class="review-info-rating-container"><span class="review-info-rating">Presentation: ${review.presentation}</span></div>
                        <div class="review-info-rating-container"><span class="review-info-rating">Outgoing: ${review.outgoing}</span></div>
                        <div class="review-info-rating-container"><span class="review-info-rating">Subject knowledge: ${review.subjectKnowledge}</span></div>
                        <div class="review-info-rating-container"><span class="review-info-rating">Listening: ${review.listening}</span></div>
                        <div class="review-info-rating-container"><span class="review-info-rating">Communication: ${review.communication}</span></div>
                        <div class="review-info-rating-container"><span class="review-info-rating">Availability: ${review.availability}</span></div>
                    </div>
                    <div class="comment-display"><span>${review.comment}</span></div>
                </div>
            </div>
            `
            }
            resultArea.innerHTML = html;
        }
        else {
                resultArea.innerHTML = "No reviews found";
            }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGetByTeacher(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let teacherName = document.getElementById("selected-teacher").value;
        this.dataStore.set("reviews", null);

        let result = await this.client.getReviewsByTeacher(teacherName, this.errorHandler);
        this.dataStore.set("reviews", result);
        if (result) {
            this.showMessage(`Reviews for ${teacherName} retrieved!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }
    async onGetByCourse(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let courseTitle = document.getElementById("selected-course").value;
        this.dataStore.set("reviews", null);

        let result = await this.client.getReviewsByTeacher(courseTitle, this.errorHandler);
        this.dataStore.set("reviews", result);
        if (result) {
            this.showMessage(`Reviews for ${courseTitle} retrieved!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreateReview(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let createReviewForm = document.getElementById("create-review-form");
        let formData = new FormData(createReviewForm);
        let teacherName = formData.get('teacherName');
        let courseTitle = formData.get('courseTitle');
        let comment = formData.get('comment');
        let presentation = formData.get('presentation');
        let availability = formData.get('availability');
        let outgoing = formData.get('outgoing');
        let listening = formData.get('listening');
        let communication = formData.get('communication');
        let subjectKnowledge = formData.get('subjectKnowledge');

        const createdReview = await this.client.createReview(
            teacherName,courseTitle, comment,presentation,
            availability,outgoing, listening, communication,
            subjectKnowledge, this.errorHandler);

        if (createdReview) {
            this.showMessage(`Created a review for ${createdReview.teacherName}`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
    async onUpdateReview(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        const updatedReview = await this.client.updateReview(
            teacherName, datePosted, comment,presentation,
            availability,outgoing, listening, communication,
            subjectKnowledge, this.errorHandler);

        if (updatedReview) {
            this.showMessage(`Updated a review for ${updatedReview.teacherName}`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
    async onDeleteReview(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();


        const deletedReview = await this.client.deleteReview(
            teacherName, datePosted, this.errorHandler);

        if (deletedReview) {
            this.showMessage(`Deleted a review for ${deletedReview.teacherName}`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const examplePage = new ExamplePage();
    examplePage.mount();
};

window.addEventListener('DOMContentLoaded', main);
