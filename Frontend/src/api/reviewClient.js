import BaseClass from "../util/baseClass";
import axios from "axios";
/**
 * Client to call the ReviewMyTeacherService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
 */
export default class ReviewClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getReviewsByTeacherName','getReviewsByCourseTitle', 'createReview', 'updateReview', 'deleteReview'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    /**
     * Makes a request to the backend to get the reviews given teacher name.
     * @param teacherName name of teacher reviews will be retrieved for.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns the list of reviews retrieved
     */
    async getReviewsByTeacherName(teacherName, errorCallback) {
        try {
            const response = await this.client.get(`/api/v1/reviewMyTeacher/teacher/${teacherName}`);
            return response.data;
        } catch (error) {
            this.handleError("getReviewsByTeacher", error, errorCallback)
        }
    }

    /**
     * Makes a request to the backend to get the reviews for the given course title.
     * @param courseTitle title of course reviews will be retrieved for.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns the list of reviews retrieved
     */
    async getReviewsByCourseTitle(courseTitle, errorCallback) {
        try {
            const response = await this.client.get(`/api/v1/reviewMyTeacher/course/${courseTitle}`);
            return response.data;
        } catch (error) {
            this.handleError("getReviewsByCourse", error, errorCallback)
        }
    }
    async getReviewsByUsername(courseTitle, errorCallback) {
        try {
            const response = await this.client.get(`/api/v1/reviewMyTeacher/user/{username}`);
            return response.data;
        } catch (error) {
            this.handleError("getReviewsByCourse", error, errorCallback)
        }
    }

    /**
     * Makes a request to the backend to create a review.
     * @param teacherName name of the teacher the review is for.
     * @param courseTitle title of the course the user had the teacher.
     * @param username username of the user posting comment.
     * @param comment an optional field for the user to leave a comment on their review. Defaults to empty string
     * @param presentation (number) rating the user gave on the teacher's presentation skills.
     * @param availability (number) rating the user gave on the teacher's availability.
     * @param outgoing (number) rating the user gave on how outgoing the teacher was.
     * @param listening (number) rating the user gave on the teacher's listening skills.
     * @param communication (number) rating the user gave on the teacher's communication skills.
     * @param subjectKnowledge (number) rating the user gave on the teacher's subject knowledge of the course
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns the full created review
     */
    async createReview(teacherName, courseTitle, username, comment = "", presentation,
                       availability, outgoing, listening, communication,
                       subjectKnowledge, errorCallback) {
        try {
            const response =
                await this.client.post(`/api/v1/reviewMyTeacher/teacher/${teacherName}`, {
                        teacherName: teacherName,
                        courseTitle: courseTitle,
                        username: username,
                        comment: comment,
                        presentation: presentation,
                        availability: availability,
                        outgoing: outgoing,
                        listening: listening,
                        communication: communication,
                        subjectKnowledge: subjectKnowledge
                });
            return response.data;
        } catch (error) {
            this.handleError("createReview", error, errorCallback);
        }
    }

    /**
     * Makes a request to the backend to update an existing review. Any unchanged fields will remain the same.
     * @param teacherName name of the teacher the review is for.
     * @param datePosted the timestamp the review was created at.
     * @param comment the updated comment for the review.
     * @param presentation the updated (number) rating for presentation skills of the teacher on the review.
     * @param availability the updated (number) rating for availability of the teacher on the review.
     * @param outgoing the updated (number) rating for how outgoing the teacher is on the review.
     * @param listening the updated (number) rating for listening skills of the teacher on the review.
     * @param communication the updated (number) rating for communication skills of the teacher on the review.
     * @param subjectKnowledge the updated (number) rating for teacher's subject knowledge of course on the review.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns the full updated review
     */
    async updateReview(teacherName,datePosted, comment, presentation,
                       availability, outgoing, listening, communication,
                       subjectKnowledge, errorCallback) {
        try {
            const response = await this.client.put(`/api/v1/reviewMyTeacher/teacher/${teacherName}`, {
                    teacherName: teacherName,
                    datePosted: datePosted,
                    comment: comment,
                    presentation: presentation,
                    availability: availability,
                    outgoing: outgoing,
                    listening: listening,
                    communication: communication,
                    subjectKnowledge: subjectKnowledge
                }
            );
            return response.data;
        } catch (error) {
            this.handleError("updateReview", error, errorCallback);
        }
    }

    /**
     * Makes a request to the backend to delete an existing review.
     * @param teacherName name of the teacher the review is for.
     * @param datePosted the timestamp the review was created at.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns response with status code 202 if successful, 404 not found if not.
     */
    async deleteReview(teacherName, datePosted, errorCallback) {
        try {
            const response = await this.client.delete(`/api/v1/reviewMyTeacher/teacher/${teacherName}`, {
                    teacherName: teacherName,
                    datePosted: datePosted,
                });
            return response.data;
        } catch (error) {
            this.handleError("deleteReview", error, errorCallback);
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }
}
