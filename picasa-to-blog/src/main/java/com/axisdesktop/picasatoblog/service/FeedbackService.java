package com.axisdesktop.picasatoblog.service;

import com.axisdesktop.picasatoblog.model.FeedbackForm;

public interface FeedbackService {
	void sendFeedback( FeedbackForm feedbackForm );

	void persistFeedback();
}
