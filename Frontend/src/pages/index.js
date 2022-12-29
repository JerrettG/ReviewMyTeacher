import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ReviewClient from "../api/reviewClient";
import { DateTimeFormatter, LocalDateTime } from 'js-joda'

/**
 * Logic needed for the view playlist page of the website.
 */
class IndexPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods([
            'onCreateReview', 'onGetByTeacherName',
            'onGetByCourseTitle', 'onGetByUsername','onUpdateReview',
            'onDeleteReview', 'renderReviews','editFunctionality','sortAscending','sortDescending'], this);
        this.dataStore = new DataStore();
    }

    async mount() {
        document.getElementById('search-by-teacher-form').addEventListener('submit', this.onGetByTeacherName);
        document.getElementById('search-by-course-form').addEventListener('submit', this.onGetByCourseTitle);
        document.getElementById('create-review-form').addEventListener('submit', this.onCreateReview);
        document.getElementById('account-link').addEventListener('click', this.onGetByUsername);
        document.getElementById('ascending-sort-button').addEventListener('click', this.sortAscending);
        document.getElementById('descending-sort-button').addEventListener('click', this.sortDescending);
        this.client = new ReviewClient();

        this.dataStore.addChangeListener(this.renderReviews);
        this.dataStore.addChangeListener(this.editFunctionality);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderReviews() {
        let resultArea = document.getElementById("result-area");

        const reviews = await this.dataStore.get("reviews");
        const getReviewsForUser = await this.dataStore.get('getReviewsForUser');
        let html = '';
        if (reviews && reviews.length > 0) {
            for (let review of reviews) {
                let totalRating = (Math.round(review.totalRating * 100) / 100).toFixed(2);
                let date = LocalDateTime.parse(review.datePosted);
                let datePosted = date.format(DateTimeFormatter.ofPattern('M/d/yyyy'));
                if (getReviewsForUser) {
                        html +=
                            `
                            <form class="review-container" onsubmit="onUpdateReview(this)">
                                
                                    <div class="review-info">
                                        <div class="review-info-top-container">
                                            <div class="course-teacher-total-container">
                                                <div class="total-rating-container"><span class="total-rating">${totalRating}</span></div>
                                                <span class="course-title">Course Title: ${review.courseTitle}</span>
                                                <input type="hidden" value="${review.courseTitle}" name="courseTitle">
                                                <span class="teacher-name">Teacher Name: ${review.teacherName}</span>
                                                <input type="hidden" value="${review.teacherName}" name="teacherName">
                                                <span>Username: ${review.username}</span>
                                                <input type="hidden" value="${review.username}" name="username">
                                                <span class="date-posted">Date Posted: ${datePosted}</span>
                                                <input type="hidden" value="${review.datePosted}" name="datePosted">
                                            </div>
                                            <div class="update-delete-buttons">
                                                <button type="submit" class="save-button" disabled>Save</button>
                                                <button type="reset" class="cancel-edit" disabled>Cancel</button>
                                                <span class="edit-review">Edit</span>
                                                <span class="delete-review">Delete</span>
                                            </div>
                                        </div>
                                        <fieldset disabled>
                                            <div class="review-info-mid-container">
                                                <span class="review-info-rating">Presentation: </span><input type="number" min="1.0" max="5.0" value="${review.presentation}" class="rating-input" name="presentation">
                                                <span class="review-info-rating">Outgoing: </span><input  type="number" min="1.0" max="5.0" value="${review.outgoing}" class="rating-input" name="outgoing">
                                                <span class="review-info-rating">Subject knowledge: </span><input  type="number" min="1.0" max="5.0" value="${review.subjectKnowledge}" class="rating-input" name="subjectKnowledge">
                                                <span class="review-info-rating">Listening: </span><input type="number" min="1.0" max="5.0"  value="${review.listening}" class="rating-input" name="listening">
                                                <span class="review-info-rating">Communication: </span><input type="number" min="1.0" max="5.0" value="${review.communication}" class="rating-input" name="communication">
                                                <span class="review-info-rating">Availability: </span><input  type="number" min="1.0" max="5.0" value="${review.availability}" class="rating-input" name="availability">
                                            </div>
                                        <div class="comment-display"><textarea class="comment" name="comment">${review.comment}</textarea></div>
                                        </fieldset>
                                    </div>
                            </form>
                            `
                }
                else {
                    html +=
                        `
                            <div class="review-container">
                                <div class="review-info">
                                    <div class="review-info-top-container">
                                        <div class="total-rating-container"><span class="total-rating">${totalRating}</span></div>
                                        <span class="course-title">Course Title: ${review.courseTitle}</span>
                                        <span class="teacher-name">Teacher Name: ${review.teacherName}</span>
                                        <span class="date-posted">Date Posted: ${datePosted}</span>
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
        if (window.onload) {
            this.editFunctionality();
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

    async onGetByTeacherName(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let teacherName = document.getElementById("selected-teacher").value;
        this.dataStore.set("reviews", null);

        let result = await this.client.getReviewsByTeacherName(teacherName, this.errorHandler);
        this.dataStore.set("reviews", result.reverse());
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
        this.dataStore.set("reviews", result.reverse());
        this.dataStore.set("getReviewsForUser", false);
        if (result) {
            this.showMessage(`Reviews for ${courseTitle} retrieved!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onGetByUsername(event) {
        let username = document.getElementById("logged-in-welcome").innerText;
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
        grecaptcha.reset();
        let createReviewForm = document.getElementById("create-review-form");
        let formData = new FormData(createReviewForm);
        let teacherName = formData.get('teacherName');
        let courseTitle = formData.get('courseTitle');
        let username =  document.getElementById('logged-in-welcome').innerText;
        let comment = formData.get('comment');
        comment = comment.replaceAll(/[`~@#$^*_;<>\{\}\[\]\\\/]/gi, '');
        console.log(comment);
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
        const form = event.srcElement.closest('.review-container');
        let formData = new FormData(form);

        let teacherName = formData.get('teacherName');
        let datePosted = formData.get('datePosted');
        let courseTitle = formData.get('courseTitle');
        let comment = formData.get('comment');
        let username = formData.get('username');
        let presentation = formData.get('presentation');
        let outgoing = formData.get('outgoing');
        let subjectKnowledge = formData.get('subjectKnowledge');
        let listening = formData.get('listening');
        let communication = formData.get('communication');
        let availability = formData.get('availability');

        const updatedReview = await this.client.updateReview(
            teacherName, datePosted, courseTitle, comment, username, presentation,
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
            let deleteButton = event.srcElement;

            let form = deleteButton.closest('.review-container');
            let formData = new FormData(form);

            let teacherName = formData.get('teacherName');
            let datePosted = formData.get('datePosted');
            let courseTitle = formData.get('courseTitle');

            const deletedReview = await this.client.deleteReview(
                teacherName, datePosted, courseTitle, this.errorHandler);

            if (deletedReview) {
                form.remove();
                this.showMessage(`Deleted a review for ${teacherName}`)
            } else {
                this.errorHandler("Error creating!  Try again...");
            }
        }
    }

    enableForm(event) {
        const editButton = event.srcElement;
        const fieldset = editButton.closest('.review-info-top-container').nextElementSibling;
        let ratingInputs = fieldset.getElementsByClassName('rating-input');
        for (let ratingInput of ratingInputs) {
            ratingInput.style.border = 'solid thin white';
        }
        let cancelButton = editButton.previousElementSibling;
        cancelButton.disabled = false;
        cancelButton.previousElementSibling.disabled = false;
        fieldset.getElementsByClassName('comment').item(0).style.border = 'solid thin gray';
        fieldset.disabled = false;
    }
    disableForm(event) {
        const cancelButton = event.srcElement;
        let saveButton = cancelButton.previousElementSibling;
        cancelButton.disabled = true;
        saveButton.disabled = true;
        const form = cancelButton.closest('.review-container');
        form.reset();
        let ratingInputs = form.getElementsByClassName('rating-input');
        for (let ratingInput of ratingInputs) {
            ratingInput.style.border = 'solid thin var(--chalk-brd-green)';
        }
        form.getElementsByClassName('comment').item(0).style.border = 'solid thin lightgray';
        const fieldset = editButton.closest('.review-info-top-container').nextElementSibling;
        fieldset.disabled = true;
    }

    editFunctionality(event) {

        const editButtons = document.querySelectorAll('.edit-review');
        const cancelButtons = document.querySelectorAll('.cancel-edit');
        const deleteButtons = document.querySelectorAll('.delete-review');
        const saveButtons = document.querySelectorAll('.save-button');
        for (let button of editButtons) {
            button.addEventListener('click', this.enableForm);
        }
        for (let button of cancelButtons) {
            button.addEventListener('click', this.disableForm);
        }
        for (let button of deleteButtons) {
            button.addEventListener('click', this.onDeleteReview);
        }
        for (let button of saveButtons) {
            button.addEventListener('click', this.onUpdateReview);
        }
    }
    sortAscending(){
        const reviews = this.dataStore.get("reviews");
        reviews.sort(function (a, b){
            return a.totalRating - b.totalRating;
        });
        this.dataStore.set("reviews", reviews);

    }
    sortDescending(){
        const reviews = this.dataStore.get("reviews");
        reviews.sort(function (a, b){
            return a.totalRating - b.totalRating;
        }).reverse();
        this.dataStore.set("reviews", reviews);

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