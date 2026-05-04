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
              private NotificationLevel level;
      
              @JsonProperty("description")
              private String description;
      
              @JsonProperty("title")
              private String title;
      
              @JsonProperty("displayTime")
              private int displayTimeMillis;
      
              @JsonProperty("width")
              private double width;
      
              @JsonProperty("height")
              private double height;
      
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
          this.description = message; 
          this.displayTimeMillis = displayTimeMillis;
          this.width = width;
          this.height = height;
    }

      /**
       * Creates a new Notification with the given parameters and default display time, width and height
       * 
       * @param level the {@link NotificationLevel} of the notification
       * @param title the title of the notification  
       * @param message the description of the notification
       */
      public Notification (NotificationLevel level, String title, String description) {
        this(level, title, description, 3000, 350, -1);
      }

      /**
       * Updates the level of this notification 
       * 
       * @param level the level to set the notification to 
       */
      public void setLevel(NotificationLevel level) {
        this.level = level;
      }

      /**
       * @return the level of this notification
       */
      public NotificationLevel getLevel() {
        return level;
      }

      /**
       * Updates the title of this notification 
       * 
       * @param title the title to set the notification to 
       */
      public void setTitle(String title){
        this.title = title;
      }

      /**
       * Gets the title of this notification
       * 
       * @return the title of this notification 
       */
      public String getTitle(){
        return title;
      }

      /**
       * Updates the description of this notification 
       * 
       * @param message the description to set the notification to 
       */
      public void setDescription(String description){
        this.description = description;
      }

      /**
       * Gets the description of this notification
       * 
       * @return the description of this notification 
       */
      public String getDescription(){
        return description;
      }

      /** 
       * Updates the display time of this notification
       * 
       * @param seconds the display time in seconds to set the notification to.
       */
      public void setDisplayTimeSeconds(double seconds){
        setDisplayTimeMillis((int) Math.round(seconds*1000));
      }

      /**
       * Updates the display time of this notification
       * 
       * @param milliseconds the display time in milliseconds to set the notification to.
       */
      public void setDisplayTimeMillis(double milliseconds){
        this.displayTimeMillis = displayTimeMillis;
      }

      /**
       * Gets the display time of this notification in milliseconds 
       * 
       * @return the number of milliseconds that the notification is displayed for. 
       */
      public int getDisplayTimeMillis(){
        return displayTimeMillis;
      }

      /**
       * Updates the width of this notification 
       * 
       * @param width the width to set the notification for
       */
      public void setWidth(double width){
        this.width = width;
      }

      public double getWidth(){
        return width;
      }

      /**
       * Updates the height of this notification 
       * 
       * <p> if the height is set to -1, the height will be determined automatically by the dashboard
       * 
       * @param height the height of the notification
       */
      public void setHeight(double height){
        this.height = height;
      }

      /**
       * Gets teh height of this notification 
       * 
       * @return height of the notification 
       */
      public double getHeight(){
        return height;
      }

      /** 
       * Modifies the notification's level and returns this notification for method chaining 
       * 
       * @param level the level to set the notification to 
       * @return the current notification 
       */
      public Notification withLevel(NotificationLevel level){
        this.level = level;
        return this;
      }

      /** 
       * Modifies the notification's title and returns this notification for method chaining 
       * 
       * @param title the title to set the notification to 
       * @return the current notification 
       */
      public Notification withTitle(String title){
        this.title = title;
        return this;
      }

      /**
       * Modifies the notification's description and returns this notification for method chaining
       * 
       * @param message the description to set the notification to
       * @return the current notification
       */
      public Notification withDescription(String description){
        this.description = description;
        return this;
      }

      /**
       * Modifies the notification's display time and returns itself to allow for method chaining
       * 
       * @param seconds the number of seconds to set the display time for
       * @return the current notification
       */
      public Notification withDisplaySeoconds(double seconds){
        setDisplayTimeSeconds(seconds);
        return this;
      }

      /**
       * Modifies the notification's display time and returns itself for method chaining
       * 
       * @param milliseconds the number of milliseconds to set the display time for
       * @return the current notification
       */
      public Notification withDisplayMillis(double milliseconds){
        setDisplayTimeMillis(milliseconds);
        return this;
      }
      
      /**
       * Modifies the notification's width and returns itself for method chaining
       * 
       * @param width the width in pixels to set the notification to
       * @return the current notification
       */
      public Notification withWidth(double width){
        setWidth(width);
        return this;
      }

      /**
       * Modifies the notification's height and returns itself for method chaining
       * 
       * @param height the height in pixels to set the notification to. If this is set to -1, the height will be determined automatically by the dashboard
       * @return the current notification
       */
      public Notification withHeight(double height){
        setHeight(height);
        return this;
      }

      /**
       * Modifies the notification's height and returns itself for method chaining 
       * 
       * <p> This will set the height to -1 to have it automatically determined by the dashboard
       * 
       * @return the current notification
       */
      public Notification withAutoHeight(){
        setHeight(-1);
        return this;
      }

      /**
       * Modifies the notification to disable the auto dismiss behavior 
       * 
       * <p> This sets the display time to 0 milliseconds 
       * 
       * <p> The auto dismiss behavior can be re-enabled by setting the display time to a number 
       * greater than 0
       * 
       * THis is because the source code for the dashboard time measurements only considers numbers 
       * greater than 0 to be valid display times, so setting the display time to 0 or less will 
       * make the dashboard ignore the display time and not dimiss the display until the user 
       * turns the dashbaord off. Lol ts coooool
       */
      public Notification withIndefiniteDisplay(){
        setDisplayTimeMillis(0);
        return this;
      }

      /**
       * Represents the possible levels of a notification. 
       * The level of a notification determines the color that it is displayed with on the dashboard, 
       * with each level corresponding to a different color. 
       * 
       * The levels are INFO (blue), WARNING (yellow), and ERROR (red)
       */
      public enum NotificationLevel{
        INFO,
        WARNING,
        ERROR
      }

}
}