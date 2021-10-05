#Example app for class

#Load in libraries
library(shiny)
library(tidyverse)
library(tidymodels)
library(rpart)
library(rpart.plot)

# We're going to build a Shiny app for interactive hyperparameter tuning.

#First, we're going to build the user-interface - the visual part of our web app. If you're familiar with HTML, some of this
#Might be familiar to you. Essentially, we're using tags to build HTML behind the scenes.
ui <- fluidPage(

    # Application title
    titlePanel("Hyperparameter Tuning - An App"),

    # Sidebar Panel
    sidebarLayout(
        sidebarPanel(
            #h2 stands for header two
            h2("Hyperparameter Tuning Dashboard"),
            #We're going to build sliders for each of our three hyperparameters, to see how they impact model performance.
            sliderInput("cost_complexity",
                        "Cost Complexity:",
                        min = 0.00,
                        max = 0.5,
                        value = 0.00,
                        step = 0.05),
            sliderInput("min_n",
                        "Minimum N:",
                        min = 0,
                        max = 50,
                        value = 0,
                        step = 2),
            sliderInput("tree_depth",
                        "Tree Depth:",
                        value = 2,
                        min = 2,
                        max = 7),
            # Setting up an action button
            actionButton("re_split", "Split the Data")
        ),
    mainPanel(
        tabsetPanel(type = "tabs",
            #One tab is going to have our plots
            tabPanel("Plots",
                h1("Plotting Results"),
                radioButtons("plot_type",  "Choose a plot:",
                     choices = list("Tree" = "dec_tree", "Predictions" = "preds"), selected = "dec_tree"),
                plotOutput("decisiontree")
        ),
        # A second tab is going to have our confusion matrix
            tabPanel("Confusion Matrix",
                 h1("Displaying a Confusion Matrix"),
                 verbatimTextOutput("confmatrix"),
                 h3("Plus some metrics"),
                 em("Metrics include Accuracy, and Kappa"),
                 verbatimTextOutput("metrics")
                 )
    )
)
    )
)

# Define server logic required to draw a histogram
server <- function(input, output) {
    #THIS IS WHERE WE PUT STUFF THAT NEVER CHANGES, NO MATTER WHAT OUR INPUTS ARE
    data(penguins)
    
    penguins <- penguins %>% filter(complete.cases(.))

    #HERE'S WHERE YOU PUT STUFF THAT WE WANT TO 'REACT' TO CHANGES
    
    #Event reactive means that this object is specifically reactive to an event (the clicking of our action button)
split <- eventReactive(input$re_split, {
    peng_split <- initial_split(penguins)
    #Leave our variable accessible (since this is technically a function)
    peng_split
})

peng_train <- reactive({
    peng_train <- training(split())
    peng_train
})

peng_test <- reactive({
    peng_test <- testing(split())
    peng_test
})

model_specs <- reactive({
    dt_spec <- decision_tree(tree_depth = input$tree_depth, cost_complexity = input$cost_complexity, min_n = input$min_n) %>%
        set_engine("rpart") %>%
        set_mode("classification")
        
    
    dt_wf <- workflow() %>%
        add_model(dt_spec) %>%
        add_formula(species ~ .)
    
    dt_wf
})
    
    
fit_model <- reactive({
    # Training our model on our training data
    train_fit <- fit(model_specs(), peng_train())
    
    
    train_fit
})

model_predictions <- reactive({
    # Testing our model on our test data
    fit_model() %>%
        predict(peng_test()) %>%
        bind_cols(peng_test())
})

#renderPlot creates a plot object for our user interface to display
output$decisiontree <- renderPlot({
    #Based on the input from the radio button, we want to choose what to display
       if (input$plot_type == "dec_tree"){
           tree_fit <- fit_model() %>%
               pull_workflow_fit()
           
           rpart.plot(tree_fit$fit, roundint =FALSE)
       } else if (input$plot_type == "preds"){
            model_predictions() %>%
               mutate(species = as.character(species), .pred_class = as.character(.pred_class),
                      guesses = if_else(.pred_class == species, species, "WRONG GUESS")) %>%
               ggplot(aes(x = flipper_length_mm, y = body_mass_g, color = guesses)) +
                    geom_point()
       }
})

# renderPrint creates a text object for our app to display
output$confmatrix <- renderPrint({
    model_predictions() %>%
        conf_mat(estimate = .pred_class, truth = species)
})

output$metrics <- renderPrint({
    model_predictions() %>%
        metrics(estimate = .pred_class, truth = species)
})


}
# Run the application 
shinyApp(ui = ui, server = server)
