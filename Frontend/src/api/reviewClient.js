import BaseClass from "../util/baseClass";
import axios from 'axios'

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
 */
export default class ReviewClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'getExample', 'createExample'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }

    /**
     * Gets the concert for the given ID.
     * @param id Unique identifier for a concert
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The concert
     */
    async getReviewsByTeacher(teacherName, errorCallback) {
        try {
            const response = await this.client.get(`/api/v1/reviewMyTeacher/teacher/${teacherName}`);
            return response.data;
        } catch (error) {
            this.handleError("getReviewsByTeacher", error, errorCallback)
        }
    }

    async getReviewsByCourse(courseTitle, errorCallback) {
        try {
            const response = await this.client.get(`/api/v1/reviewMyTeacher/course/${courseTitle}`);
            return response.data;
        } catch (error) {
            this.handleError("getReviewsByCourse", error, errorCallback)
        }
    }

    async createReview(teacherName, courseTitle, comment, presentation,
                       availability, outgoing, listening, communication,
                       subjectKnowledge, errorCallback) {
        try {
            const response = await this.client.post(`/api/v1/reviewMyTeacher/teacher/${teacherName}`, {
                teacherName: teacherName,
                courseTitle: courseTitle,
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
            });
            return response.data;
        } catch (error) {
            this.handleError("updateReview", error, errorCallback);
        }
    }
    async deleteReview(teacherName, datePosted) {
        try {
            const response = await this.client.post(`/api/v1/reviewMyTeacher/teacher/${teacherName}`, {
                teacherName: teacherName,
                datePosted: datePosted
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
