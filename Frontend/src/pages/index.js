import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ReviewClient from "../api/reviewClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class IndexPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods([
            'onCreateReview', 'onGetByTeacherName',
            'onGetByCourseTitle', 'onUpdateReview',
            'onDeleteReview', 'renderReviews', 'enableForm'], this);
        this.dataStore = new DataStore();
    }

    async mount() {
        document.getElementById('search-by-teacher-form').addEventListener('submit', this.onGetByTeacherName);
        document.getElementById('search-by-course-form').addEventListener('submit', this.onGetByCourseTitle);
        document.getElementById('create-review-form').addEventListener('submit', this.onCreateReview);
        document.getElementById('account-link').addEventListener('click', this.onGetByUsername);
        const editButtons = document.querySelectorAll('.edit-review');
        const cancelButtons = document.querySelectorAll('.cancel-edit');
        const deleteButtons = document.querySelectorAll('.delete-review');

        for (let button of editButtons) {
            button.addEventListener('click', this.enableForm);
        }
        for (let button of cancelButtons) {
            button.addEventListener('click', this.disableForm);
        }
        for (let button of deleteButtons) {
            button.addEventListener('click', this.onDeleteReview);
        }
        this.client = new ReviewClient();

        this.dataStore.addChangeListener(this.renderReviews())
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderReviews() {
        let resultArea = document.getElementById("result-area");

        const reviews = await this.dataStore.get("reviews");
        const getReviewsForUser = await this.dataStore.get('getReviewsForUser');
        let html = '';
        if (reviews && reviews.length > 0) {
            for (let review of reviews) {
                if (getReviewsForUser) {
                        html +=
                            `
                            <form class="review-container" onsubmit="onUpdateReview(this)">
                                <fieldset disabled>
                                    <div class="review-info">
                                        <div class="review-info-top-container">
                                            <div class="course-teacher-total-container">
                                                <div class="total-rating-container"><span class="total-rating">4.0</span></div>
                                                <span class="course-title">Course title: ${review.courseTitle}</span>
                                                <span class="teacher-name">Teacher name: ${review.teacherName}</span>
                                                <input type="hidden" value="${review.teacherName}" name="teacherName">
                                                <span class="date-posted">Date Posted: ${review.datePosted}</span>
                                                <input type="hidden" value="${review.datePosted}" name="datePosted">
                                            </div>
                                            <div class="update-delete-buttons">
                                                <button type="submit">Save</button>
                                                <button type="reset" class="cancel-edit">Cancel</button>
                                                <span class="edit-review">Edit</span>
                                                <span class="delete-review">Delete</span>
                                            </div>
                                        </div>
                                        <div class="review-info-mid-container">
                                            <span class="review-info-rating">Presentation: </span><input type="number" min="1.0" max="5.0" value="${review.presentation}" class="rating-input" name="presentation">
                                            <span class="review-info-rating">Outgoing: </span><input  type="number" min="1.0" max="5.0" value="${review.outgoing}" class="rating-input" name="outgoing">
                                            <span class="review-info-rating">Subject knowledge: </span><input  type="number" min="1.0" max="5.0" value="${review.subjectKnowledge}" class="rating-input" name="subjectKnowledge">
                                            <span class="review-info-rating">Listening: </span><input type="number" min="1.0" max="5.0"  value="${review.listening}" class="rating-input" name="listening">
                                            <span class="review-info-rating">Communication: </span><input type="number" min="1.0" max="5.0" value="${review.communication}" class="rating-input" name="communication">
                                            <span class="review-info-rating">Availability: </span><input  type="number" min="1.0" max="5.0" value="${review.availability}" class="rating-input" name="availability">
                                        </div>
                                        <div class="comment-display"><textarea class="comment" placeholder="${review.comment}" name="comment"></textarea></div>
                                    </div>
                                </fieldset>
                            </form>
                            `
                }
                else {
                    html +=
                        `
                            <div class="review-container">
                                <div class="review-info">
                                    <div class="review-info-top-container">
                                        <div class="total-rating-container"><span class="total-rating">${review.totalRating}</span></div>
                                        <span class="course-title">Course title: ${review.courseTitle}</span>
                                        <span class="teacher-name">Teacher name: ${review.teacherName}</span>
                                        <span class="date-posted">Date Posted: ${review.datePosted}</span>
                                    </div>
                                    <div class="review-info-mid-container">
                                        <span class="review-info-rating">Presentation: ${review.presentation}</span>
                                        <span class="review-info-rating">Outgoing: ${review.outgoing}</span>
                                        <span class="review-info-rating">Subject knowledge: ${review.subjectKnowledge}</span>
                                        <span class="review-info-rating">Listening: ${review.listening}</span>
                                        <span class="review-info-rating">Communication: ${review.communication}</span>
                                        <span class="review-info-rating">Availability: ${review.availability}</span>
                                    </div>
                                    <div class="comment-display"><span>${review.comment}</span></div>
                                </div>
                            </div>
                       `
                }
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
        this.dataStore.set("getReviewsForUser", false);
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
        this.dataStore.set("getReviewsForUser", false);
        if (result) {
            this.showMessage(`Reviews for ${courseTitle} retrieved!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onGetByUsername(event) {
        event.preventDefault();

        let username = document.getElementById("logged-in-welcome").value;
        this.dataStore.set("reviews", null);

        let result = await this.client.getReviewsByUsername(username, this.errorHandler);
        this.dataStore.set("reviews", result);
        this.dataStore.set("getReviewsForUser", true);
        if (result) {
            this.showMessage(`Reviews for ${username} retrieved!`)
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
        let formData = new FormData(event);

        let teacherName = formData.get('teacherName');
        let datePosted = formData.get('datePosted');
        let comment = formData.get('comment');
        let presentation = formData.get('presentation');
        let outgoing = formData.get('outgoing');
        let subjectKnowledge = formData.get('subjectKnowledge');
        let listening = formData.get('listening');
        let communication = formData.get('communication');
        let availability = formData.get('availability');

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
        event.preventDefault();
        let result = confirm("Are you sure you want to delete?");
        if (result) {
            // Prevent the page from refreshing on form submit
            console.log("this worked");
            let deleteButton = event.srcElement;
            let form = deleteButton.closest('.review-container');
            let formData = new FormData(form);
            let teacherName = formData.get('teacherName');
            let datePosted = formData.get('datePosted');

            const deletedReview = await this.client.deleteReview(
                teacherName, datePosted, this.errorHandler);

            if (deletedReview) {
                this.showMessage(`Deleted a review for ${deletedReview.teacherName}`)
            } else {
                this.errorHandler("Error creating!  Try again...");
            }
        }
    }

    enableForm(event) {
        const fieldset = event.srcElement.closest('fieldset');
        fieldset.disabled = false;
    }
    disableForm(event) {
        const form = event.srcElement.closest('.review-container');
        form.reset();
        const fieldset = event.srcElement.closest('fieldset');
        fieldset.disabled = true;
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