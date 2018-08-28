;

var kanbanData = live2dData;

var pixiKanban;
(function (pixiKoharu) {

    loadAssets();
    PIXI.loader
        .load(function (loader, resources) {
            var app = new PIXI.Application(1280, 720, { backgroundColor: 0x1099bb });
            document.body.appendChild(app.view);
            var moc = LIVE2DCUBISMCORE.Moc.fromArrayBuffer(resources[kanbanData.moc.key].data);
            var model = new LIVE2DCUBISMPIXI.ModelBuilder()
                .setMoc(moc)
                .setTimeScale(1)
                .addTexture(0, resources[kanbanData.textures[0].key].texture)
                .addAnimatorLayer("base", LIVE2DCUBISMFRAMEWORK.BuiltinAnimationBlenders.OVERRIDE, 1)
                .build();
            app.stage.addChild(model);
            app.stage.addChild(model.masks);
            var animation = LIVE2DCUBISMFRAMEWORK.Animation.fromMotion3Json(resources[kanbanData.motions[0].key].data);
            model.animator
                .getLayer("base")
                .play(animation);
            app.ticker.add(function (deltaTime) {
                model.update(deltaTime);
                model.masks.update(app.renderer);
            });
            var onResize = function (event) {
                if (event === void 0) { event = null; }
                var width = window.innerWidth;
                var height = (width / 16.0) * 9.0;
                app.view.style.width = width + "px";
                app.view.style.height = height + "px";
                app.renderer.resize(width, height);
                model.position = new PIXI.Point((width * 0.5), (height * 0.5));
                model.scale = new PIXI.Point((model.position.x * 0.8), (model.position.x * 0.8));
                model.masks.resize(app.view.width, app.view.height);
            };
            onResize();
            window.onresize = onResize;


            var onClick = function (event) {
                console.log(111);
                var nextAnimation = LIVE2DCUBISMFRAMEWORK.Animation.fromMotion3Json(resources[kanbanData.motions[1].key].data);
                model.animator
                    .getLayer("base")
                    .play(nextAnimation);
            };
            app.view.addEventListener('click', onClick, true);
        });
})(pixiKanban || (pixiKanban = {}));

/**
 * 加载资源
 * */
function loadAssets() {
    {
        // 加载moc
        PIXI.loader.add(kanbanData.moc.key, kanbanData.moc.path, { xhrType: PIXI.loaders.Resource.XHR_RESPONSE_TYPE.BUFFER });
    }


    {
        // 加载贴图
        var textures = kanbanData.textures;
        if (textures && textures instanceof Array && textures.length > 0) {
            textures.forEach(function(texture) {
                PIXI.loader.add(texture.key, texture.path);
            });
        }
    }

    {
        // 加载物理文件
        var physics = kanbanData.physics;
        if (physics) {
            PIXI.loader.add(physics.key, physics.path, { xhrType: PIXI.loaders.Resource.XHR_RESPONSE_TYPE.JSON });
        }
    }

    {
        // 加载motion文件
        var motions = kanbanData.motions;
        if (motions && motions instanceof Array && motions.length > 0) {
            motions.forEach(function (motion) {
                PIXI.loader.add(motion.key, motion.path, { xhrType: PIXI.loaders.Resource.XHR_RESPONSE_TYPE.JSON });
            });
        }
    }
}

/**
 * 初始化模型
 * */
function initModel(loader, resources) {
    var moc = LIVE2DCUBISMCORE.Moc.fromArrayBuffer(resources[kanbanData.moc.key].data);
    var model = new LIVE2DCUBISMPIXI.ModelBuilder()
        .setMoc(moc)
        .setTimeScale(1)
        .addTexture(0, resources[kanbanData.textures[0].key].texture)
        .addAnimatorLayer("base", LIVE2DCUBISMFRAMEWORK.BuiltinAnimationBlenders.OVERRIDE, 1)
        .build();
}
















































