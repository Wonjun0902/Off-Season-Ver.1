// Copyright (c) 2023-2025 Gold87 and other Elastic contributors
// This software can be modified and/or shared under the terms
// defined by the Elastic license:
// https://github.com/Gold872/elastic-dashboard/blob/main/LICENSE

package frc.robot.lib;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.PubSubOption;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringTopic;

public class Elastic{

    private static final StringTopic NOTIFICATION_TOPIC = NetworkTableInstance.getDefault().getStringTopic("Elastic/Notification");
    private static final StringPublisher NOTIFICATION_PUBLISHER = NOTIFICATION_TOPIC
        .publish(PubSubOption.sendAll(true), PubSubOption.keepDuplicates(true));
    private static final StringTopic SELECTED_TAB_TOPIC = NetworkTableInstance.getDefault().getStringTopic("Elastic/SelectedTab");
    private static final StringPublisher SELECTED_TAB_PUBLISHER = SELECTED_TAB_TOPIC.publish(PubSubOption.keepDuplicates(true));
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Send a notification to the Elastic Dashboard. The notification is serialized as JSON string 
     * before being published 
     * 
     * @param notification the {@link Notification} to send
     */

     public static void sendNotification(Notification notification){
        try {
            NOTIFICATION_PUBLISHER.set(OBJECT_MAPPER.writeValueAsString(notification));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
     }

     /**
      * Set the selected tab on the Elastic Dashboard. The tab name is published as a string.
      * 
      * @param tabName the name of the tab to select
      */
      public static void selectTab(String tabName){
        SELECTED_TAB_PUBLISHER.set(tabName);
      }

      /**
       * Selects the tab of the dahsboard at given index. If this index is greater or equal to 
       * the number of tabs, this will have no effect
       * 
       * @param index the index of the tab to select
       */
      public static void setSelectedTab(int index){
        selectTab(Integer.toString(index));
      }
            /**
             * Represents a notification to be sent to the Elastic Dashboard. This class is immutable and can be serialized to JSON string
             */
            public static class Notification{
              @JsonProperty("level")
              private Notification level;
      
              @JsonProperty("message")
              private Notification message;
      
              @JsonProperty("title")
              private Notification title;
      
              @JsonProperty("displayTime")
              private Notification displayTime;
      
              @JsonProperty("width")
              private Notification width;
      
              @JsonProperty("height")
              private Notification height;
      
                /**
               * Creates a new Notification with all default parameters. This constructor is intended to be
               * used with the chainable decorator methods
               *
               * <p>Title and description fields are empty.
               */
        public Notification() {
                  this(NotificationLevel.INFO, "", "");
        }

        /**
         * Creates a new Notification with the given parameters
         * 
         * @param level the {@link NotificationLevel} of the notification
         * @param title the title of the notification  
         * @param message the description of the notification
         * @param displayTime the time in seconds that the notification should be displayed on the dashboard. If this is less than or equal to 0, the notification will be displayed indefinitely
         * @param width the width of the notification in pixels. If this is less than or
         * equal to 0, the notification will have a default width
         * @param height the height of the notification in pixels. If this is less than or
         */
        public Notification(NotificationLevel level, String title, String message, double displayTime, double width, double height) {
            this.level = level;
            this.title = title;
            this.message = message;
            this.displayTime = displayTime;
            this.width = width;
            this.height = height;


    }
}
}