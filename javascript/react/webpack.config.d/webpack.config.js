config.devServer = Object.assign(
    {},
    config.devServer || {},
    {
        historyApiFallback: true,
        open: false
    }
)