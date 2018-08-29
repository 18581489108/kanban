namespace kanban {
    let kanbanData = live2dData;

    loadAssets(PIXI.loader, kanbanData);
    PIXI.loader
        .load((loader: PIXI.loaders.Loader, resources: PIXI.loaders.ResourceDictionary) => {
        // Create app.
        let app = new PIXI.Application(1280, 720, {backgroundColor : 0x1099bb});

        
        document.body.appendChild(app.view);


        // Load moc.
        let moc = LIVE2DCUBISMCORE.Moc.fromArrayBuffer(resources[kanbanData.moc.key].data);


        // Create model.
        let model = new LIVE2DCUBISMPIXI.ModelBuilder()
            .setMoc(moc)
            .setTimeScale(1)
            .addTexture(0, resources[kanbanData.textures[0].key].texture)
            .addAnimatorLayer("base", LIVE2DCUBISMFRAMEWORK.BuiltinAnimationBlenders.OVERRIDE, 1)
            .build();

        
        // Add model to stage.
        app.stage.addChild(model);
        app.stage.addChild(model.masks);


        // Load animation.
        let animation = LIVE2DCUBISMFRAMEWORK.Animation.fromMotion3Json(resources[kanbanData.motions[0].key].data);


        // Play animation.
        model.animator
            .getLayer("base")
            .play(animation);
            

        // Set up ticker.
        app.ticker.add((deltaTime) => {
            model.update(deltaTime);
            model.masks.update(app.renderer);
        });


        // Do that responsive design...
        let onResize = function (event: any = null) {
            // Keep 16:9 ratio.
            var width = window.innerWidth;
            var height = (width / 16.0) * 9.0;
            

            // Resize app.
            app.view.style.width = width + "px";
            app.view.style.height = height + "px";
            
            app.renderer.resize(width, height);


            // Resize model.
            model.position = new PIXI.Point((width * 0.5), (height * 0.5));
            model.scale = new PIXI.Point((model.position.x * 0.8), (model.position.x * 0.8));
            
            // Resize mask texture.
            model.masks.resize(app.view.width, app.view.height);

        };
        onResize();
        window.onresize = onResize;


        // TODO Clean up properly.
    });

    /**
     * 加载资源
     * 
     * @param loader
     * @param live2dData 
     */
    function loadAssets(loader : PIXI.loaders.Loader, live2dData : Live2dData) : void {
        // 加载moc
        loader.add(live2dData.moc.key, live2dData.moc.path, { xhrType: PIXI.loaders.Resource.XHR_RESPONSE_TYPE.BUFFER });
    
    
        // 加载贴图
        let textures = live2dData.textures;
        if (textures && textures.length > 0) {
            textures.forEach(function(texture) {
                loader.add(texture.key, texture.path);
            });
        }
    
        // 加载物理文件
        let physics = live2dData.physics;
        if (physics) {
            loader.add(physics.key, physics.path, { xhrType: PIXI.loaders.Resource.XHR_RESPONSE_TYPE.JSON });
        }
    
         // 加载motion文件
         let motions = live2dData.motions;
         if (motions && motions.length > 0) {
             motions.forEach(function (motion) {
                 loader.add(motion.key, motion.path, { xhrType: PIXI.loaders.Resource.XHR_RESPONSE_TYPE.JSON });
             });
         }
    }
}