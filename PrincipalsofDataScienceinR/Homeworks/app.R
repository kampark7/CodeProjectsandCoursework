library(shiny)
library(tidyverse)
library(tidymodels)
library(rpart)
library(rpart.plot)
library(ggplot2)
data(penguins)


df <- subset(penguins, select = -c(species, island, sex)) #creating a variable only data set with no character values

df <- df %>% filter(complete.cases(.))

df <- scale(df) #scaling the data to make it less suseptible to misrepresentation
df <- as.tibble(df, rownames = NA)

ui <- fluidPage(
  
  # Application title
  titlePanel("Clustering Alogrithm - An App for Penguin Data"),
  
  # Sidebar Panel
  sidebarLayout(
    sidebarPanel(
      #h2 stands for header two
      h2("Tuning Dashboard"),
      sliderInput("k_means",
                  "K Means:",
                  min = 0.00,
                  max = 10.00,
                  value = 1.00,
                  step = 1.00)
    ),
  mainPanel(
       h1("Displaying Cluster Plot"),
       em("The plot shows different ammounts of clusters when identifying Bill Depth by Bill Length"),
       plotOutput("visualplot"),
       h3("Metadata"),
       verbatimTextOutput("metrics")

  )
  )
)
# Define server logic required to draw a histogram
server <- function(input, output) {
  
  clustering <- reactive(kmeans(df, input$k_means))
  
  output$visualplot <- renderPlot({
    ggplot(df, aes(x = bill_length_mm, y = bill_depth_mm, color = clustering()$cluster)) + #plotting the data
      labs(color = "Clusters") +
      geom_point()
  })
  
  output$metrics <- renderPrint({
    clustering()
  })
  
  
}
# Run the application 
shinyApp(ui = ui, server = server)