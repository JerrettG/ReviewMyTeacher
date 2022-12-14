import BaseClass from "../util/baseClass.js";
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
     * @param teacherName
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The concert
     */
    async getReviewsByTeacherName(teacherName, errorCallback) {
        try {
            const response = await fetch(`/api/v1/reviewMyTeacher/teacher/${teacherName}`);
            return response.data;
        } catch (error) {
            this.handleError("getReviewsByTeacher", error, errorCallback)
        }
    }

    async getReviewsByCourseTitle(courseTitle, errorCallback) {
        try {
            const response = await fetch(`/api/v1/reviewMyTeacher/course/${courseTitle}`);
            return response.data;
        } catch (error) {
            this.handleError("getReviewsByCourse", error, errorCallback)
        }
    }

    async createReview(teacherName, courseTitle, username, comment, presentation,
                       availability, outgoing, listening, communication,
                       subjectKnowledge, errorCallback) {
        try {
            const response = await fetch(`/api/v1/reviewMyTeacher/teacher/${teacherName}`, {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "POST",
                body: {
<<<<<<< HEAD
                    teacherName: teacherName,
                    courseTitle: courseTitle,
                    comment: comment,
                    presentation: presentation,
                    availability: availability,
                    outgoing: outgoing,
                    listening: listening,
                    communication: communication,
                    subjectKnowledge: subjectKnowledge
=======
                teacherName: teacherName,
                courseTitle: courseTitle,
                comment: comment,
                presentation: presentation,
                availability: availability,
                outgoing: outgoing,
                listening: listening,
                communication: communication,
                subjectKnowledge: subjectKnowledge
>>>>>>> b00b3c0 (Added username retrieval from auth0 and finished up reviewClient.js)
                }
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
            const response = await fetch(`/api/v1/reviewMyTeacher/teacher/${teacherName}`,  {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "PUT",
                body: {
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
            });
            return response.data;
        } catch (error) {
            this.handleError("updateReview", error, errorCallback);
        }
    }
    async deleteReview(teacherName, datePosted) {
        try {
            const response = await fetch(`/api/v1/reviewMyTeacher/teacher/${teacherName}`,  {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "DELETE",
                body: {
                    teacherName: teacherName,
                    datePosted: datePosted,
                }
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
