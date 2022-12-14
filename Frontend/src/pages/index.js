import BaseClass from "../util/baseClass.js";
import DataStore from "../util/DataStore.js";
import ReviewClient from "../api/reviewClient.js";

/**
 * Logic needed for the view playlist page of the website.
 */
class IndexPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods([
            'onCreateReview', 'onGetByTeacherName',
            'onGetByCourseTitle', 'onUpdateReview',
            'onDeleteReview', 'renderReviews'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('search-by-teacher-form').addEventListener('submit', this.onGetByTeacherName);
        document.getElementById('search-by-course-form').addEventListener('submit', this.onGetByCourseTitle);
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
                            <div class="total-rating-container"><span class="total-rating">4.0</span></div>
                            <span class="course-title">Course title: COMPSCI-101</span>
                            <span class="teacher-name">Teacher name: Professor Teacher</span>
                        </div>
                        <div class="review-info-mid-container">
                            <span class="review-info-rating">Presentation: 3.0</span>
                            <span class="review-info-rating">Outgoing: 3.0</span>
                            <span class="review-info-rating">Subject knowledge: 3.0</span>
                            <span class="review-info-rating">Listening: 3.0</span>
                            <span class="review-info-rating">Communication: 3.0</span>
                            <span class="review-info-rating">Availability: 3.0</span>
                        </div>
                        <div class="comment-display"><span>Lorem Ipsum adlfk;jasldfjkalsdfjlaksjflksadjf</span></div>
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

    async onGetByTeacherName(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let teacherName = document.getElementById("selected-teacher").value;
        this.dataStore.set("reviews", null);

        let result = await this.client.getReviewsByTeacherName(teacherName, this.errorHandler);
        this.dataStore.set("reviews", result);
        if (result) {
            this.showMessage(`Reviews for ${teacherName} retrieved!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }
    async onGetByCourseTitle(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let courseTitle = document.getElementById("selected-course").value;
        this.dataStore.set("reviews", null);

        let result = await this.client.getReviewsByCourseTitle(courseTitle, this.errorHandler);
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
        let username = 'usernamePlaceholder';
        let comment = formData.get('comment');
        let presentation = formData.get('presentation');
        let availability = formData.get('availability');
        let outgoing = formData.get('outgoing');
        let listening = formData.get('listening');
        let communication = formData.get('communication');
        let subjectKnowledge = formData.get('subjectKnowledge');

        const createdReview = await this.client.createReview(
            teacherName, courseTitle, username, comment,presentation,
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
    const indexPage = new IndexPage();
    indexPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
