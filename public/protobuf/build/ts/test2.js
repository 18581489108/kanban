/*eslint-disable block-scoped-var, id-length, no-control-regex, no-magic-numbers, no-prototype-builtins, no-redeclare, no-shadow, no-var, sort-vars*/
"use strict";

var $protobuf = require("protobufjs/minimal");

// Common aliases
var $Reader = $protobuf.Reader, $Writer = $protobuf.Writer, $util = $protobuf.util;

// Exported root namespace
var $root = $protobuf.roots["default"] || ($protobuf.roots["default"] = {});

$root.example = (function() {

    /**
     * Namespace example.
     * @exports example
     * @namespace
     */
    var example = {};

    example.TestMessage = (function() {

        /**
         * Properties of a TestMessage.
         * @memberof example
         * @interface ITestMessage
         * @property {example.IMessage|null} [msg] TestMessage msg
         */

        /**
         * Constructs a new TestMessage.
         * @memberof example
         * @classdesc Represents a TestMessage.
         * @implements ITestMessage
         * @constructor
         * @param {example.ITestMessage=} [properties] Properties to set
         */
        function TestMessage(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * TestMessage msg.
         * @member {example.IMessage|null|undefined} msg
         * @memberof example.TestMessage
         * @instance
         */
        TestMessage.prototype.msg = null;

        /**
         * Creates a new TestMessage instance using the specified properties.
         * @function create
         * @memberof example.TestMessage
         * @static
         * @param {example.ITestMessage=} [properties] Properties to set
         * @returns {example.TestMessage} TestMessage instance
         */
        TestMessage.create = function create(properties) {
            return new TestMessage(properties);
        };

        /**
         * Encodes the specified TestMessage message. Does not implicitly {@link example.TestMessage.verify|verify} messages.
         * @function encode
         * @memberof example.TestMessage
         * @static
         * @param {example.ITestMessage} message TestMessage message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        TestMessage.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.msg != null && message.hasOwnProperty("msg"))
                $root.example.Message.encode(message.msg, writer.uint32(/* id 1, wireType 2 =*/10).fork()).ldelim();
            return writer;
        };

        /**
         * Encodes the specified TestMessage message, length delimited. Does not implicitly {@link example.TestMessage.verify|verify} messages.
         * @function encodeDelimited
         * @memberof example.TestMessage
         * @static
         * @param {example.ITestMessage} message TestMessage message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        TestMessage.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a TestMessage message from the specified reader or buffer.
         * @function decode
         * @memberof example.TestMessage
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {example.TestMessage} TestMessage
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        TestMessage.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.example.TestMessage();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1:
                    message.msg = $root.example.Message.decode(reader, reader.uint32());
                    break;
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a TestMessage message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof example.TestMessage
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {example.TestMessage} TestMessage
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        TestMessage.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a TestMessage message.
         * @function verify
         * @memberof example.TestMessage
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        TestMessage.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.msg != null && message.hasOwnProperty("msg")) {
                var error = $root.example.Message.verify(message.msg);
                if (error)
                    return "msg." + error;
            }
            return null;
        };

        /**
         * Creates a TestMessage message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof example.TestMessage
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {example.TestMessage} TestMessage
         */
        TestMessage.fromObject = function fromObject(object) {
            if (object instanceof $root.example.TestMessage)
                return object;
            var message = new $root.example.TestMessage();
            if (object.msg != null) {
                if (typeof object.msg !== "object")
                    throw TypeError(".example.TestMessage.msg: object expected");
                message.msg = $root.example.Message.fromObject(object.msg);
            }
            return message;
        };

        /**
         * Creates a plain object from a TestMessage message. Also converts values to other types if specified.
         * @function toObject
         * @memberof example.TestMessage
         * @static
         * @param {example.TestMessage} message TestMessage
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        TestMessage.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults)
                object.msg = null;
            if (message.msg != null && message.hasOwnProperty("msg"))
                object.msg = $root.example.Message.toObject(message.msg, options);
            return object;
        };

        /**
         * Converts this TestMessage to JSON.
         * @function toJSON
         * @memberof example.TestMessage
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        TestMessage.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        return TestMessage;
    })();

    example.Hello = (function() {

        /**
         * Properties of a Hello.
         * @memberof example
         * @interface IHello
         * @property {number|null} [content] Hello content
         */

        /**
         * Constructs a new Hello.
         * @memberof example
         * @classdesc Represents a Hello.
         * @implements IHello
         * @constructor
         * @param {example.IHello=} [properties] Properties to set
         */
        function Hello(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * Hello content.
         * @member {number} content
         * @memberof example.Hello
         * @instance
         */
        Hello.prototype.content = 0;

        /**
         * Creates a new Hello instance using the specified properties.
         * @function create
         * @memberof example.Hello
         * @static
         * @param {example.IHello=} [properties] Properties to set
         * @returns {example.Hello} Hello instance
         */
        Hello.create = function create(properties) {
            return new Hello(properties);
        };

        /**
         * Encodes the specified Hello message. Does not implicitly {@link example.Hello.verify|verify} messages.
         * @function encode
         * @memberof example.Hello
         * @static
         * @param {example.IHello} message Hello message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        Hello.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.content != null && message.hasOwnProperty("content"))
                writer.uint32(/* id 1, wireType 0 =*/8).int32(message.content);
            return writer;
        };

        /**
         * Encodes the specified Hello message, length delimited. Does not implicitly {@link example.Hello.verify|verify} messages.
         * @function encodeDelimited
         * @memberof example.Hello
         * @static
         * @param {example.IHello} message Hello message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        Hello.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a Hello message from the specified reader or buffer.
         * @function decode
         * @memberof example.Hello
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {example.Hello} Hello
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        Hello.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.example.Hello();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1:
                    message.content = reader.int32();
                    break;
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a Hello message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof example.Hello
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {example.Hello} Hello
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        Hello.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a Hello message.
         * @function verify
         * @memberof example.Hello
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        Hello.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.content != null && message.hasOwnProperty("content"))
                if (!$util.isInteger(message.content))
                    return "content: integer expected";
            return null;
        };

        /**
         * Creates a Hello message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof example.Hello
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {example.Hello} Hello
         */
        Hello.fromObject = function fromObject(object) {
            if (object instanceof $root.example.Hello)
                return object;
            var message = new $root.example.Hello();
            if (object.content != null)
                message.content = object.content | 0;
            return message;
        };

        /**
         * Creates a plain object from a Hello message. Also converts values to other types if specified.
         * @function toObject
         * @memberof example.Hello
         * @static
         * @param {example.Hello} message Hello
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        Hello.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults)
                object.content = 0;
            if (message.content != null && message.hasOwnProperty("content"))
                object.content = message.content;
            return object;
        };

        /**
         * Converts this Hello to JSON.
         * @function toJSON
         * @memberof example.Hello
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        Hello.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        return Hello;
    })();

    example.LoginRequest = (function() {

        /**
         * Properties of a LoginRequest.
         * @memberof example
         * @interface ILoginRequest
         * @property {number|Long|null} [uuid] LoginRequest uuid
         */

        /**
         * Constructs a new LoginRequest.
         * @memberof example
         * @classdesc Represents a LoginRequest.
         * @implements ILoginRequest
         * @constructor
         * @param {example.ILoginRequest=} [properties] Properties to set
         */
        function LoginRequest(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * LoginRequest uuid.
         * @member {number|Long} uuid
         * @memberof example.LoginRequest
         * @instance
         */
        LoginRequest.prototype.uuid = $util.Long ? $util.Long.fromBits(0,0,false) : 0;

        /**
         * Creates a new LoginRequest instance using the specified properties.
         * @function create
         * @memberof example.LoginRequest
         * @static
         * @param {example.ILoginRequest=} [properties] Properties to set
         * @returns {example.LoginRequest} LoginRequest instance
         */
        LoginRequest.create = function create(properties) {
            return new LoginRequest(properties);
        };

        /**
         * Encodes the specified LoginRequest message. Does not implicitly {@link example.LoginRequest.verify|verify} messages.
         * @function encode
         * @memberof example.LoginRequest
         * @static
         * @param {example.ILoginRequest} message LoginRequest message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        LoginRequest.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.uuid != null && message.hasOwnProperty("uuid"))
                writer.uint32(/* id 1, wireType 0 =*/8).int64(message.uuid);
            return writer;
        };

        /**
         * Encodes the specified LoginRequest message, length delimited. Does not implicitly {@link example.LoginRequest.verify|verify} messages.
         * @function encodeDelimited
         * @memberof example.LoginRequest
         * @static
         * @param {example.ILoginRequest} message LoginRequest message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        LoginRequest.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a LoginRequest message from the specified reader or buffer.
         * @function decode
         * @memberof example.LoginRequest
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {example.LoginRequest} LoginRequest
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        LoginRequest.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.example.LoginRequest();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1:
                    message.uuid = reader.int64();
                    break;
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a LoginRequest message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof example.LoginRequest
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {example.LoginRequest} LoginRequest
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        LoginRequest.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a LoginRequest message.
         * @function verify
         * @memberof example.LoginRequest
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        LoginRequest.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.uuid != null && message.hasOwnProperty("uuid"))
                if (!$util.isInteger(message.uuid) && !(message.uuid && $util.isInteger(message.uuid.low) && $util.isInteger(message.uuid.high)))
                    return "uuid: integer|Long expected";
            return null;
        };

        /**
         * Creates a LoginRequest message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof example.LoginRequest
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {example.LoginRequest} LoginRequest
         */
        LoginRequest.fromObject = function fromObject(object) {
            if (object instanceof $root.example.LoginRequest)
                return object;
            var message = new $root.example.LoginRequest();
            if (object.uuid != null)
                if ($util.Long)
                    (message.uuid = $util.Long.fromValue(object.uuid)).unsigned = false;
                else if (typeof object.uuid === "string")
                    message.uuid = parseInt(object.uuid, 10);
                else if (typeof object.uuid === "number")
                    message.uuid = object.uuid;
                else if (typeof object.uuid === "object")
                    message.uuid = new $util.LongBits(object.uuid.low >>> 0, object.uuid.high >>> 0).toNumber();
            return message;
        };

        /**
         * Creates a plain object from a LoginRequest message. Also converts values to other types if specified.
         * @function toObject
         * @memberof example.LoginRequest
         * @static
         * @param {example.LoginRequest} message LoginRequest
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        LoginRequest.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults)
                if ($util.Long) {
                    var long = new $util.Long(0, 0, false);
                    object.uuid = options.longs === String ? long.toString() : options.longs === Number ? long.toNumber() : long;
                } else
                    object.uuid = options.longs === String ? "0" : 0;
            if (message.uuid != null && message.hasOwnProperty("uuid"))
                if (typeof message.uuid === "number")
                    object.uuid = options.longs === String ? String(message.uuid) : message.uuid;
                else
                    object.uuid = options.longs === String ? $util.Long.prototype.toString.call(message.uuid) : options.longs === Number ? new $util.LongBits(message.uuid.low >>> 0, message.uuid.high >>> 0).toNumber() : message.uuid;
            return object;
        };

        /**
         * Converts this LoginRequest to JSON.
         * @function toJSON
         * @memberof example.LoginRequest
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        LoginRequest.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        return LoginRequest;
    })();

    example.LoginResponse = (function() {

        /**
         * Properties of a LoginResponse.
         * @memberof example
         * @interface ILoginResponse
         * @property {boolean|null} [result] LoginResponse result
         */

        /**
         * Constructs a new LoginResponse.
         * @memberof example
         * @classdesc Represents a LoginResponse.
         * @implements ILoginResponse
         * @constructor
         * @param {example.ILoginResponse=} [properties] Properties to set
         */
        function LoginResponse(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * LoginResponse result.
         * @member {boolean} result
         * @memberof example.LoginResponse
         * @instance
         */
        LoginResponse.prototype.result = false;

        /**
         * Creates a new LoginResponse instance using the specified properties.
         * @function create
         * @memberof example.LoginResponse
         * @static
         * @param {example.ILoginResponse=} [properties] Properties to set
         * @returns {example.LoginResponse} LoginResponse instance
         */
        LoginResponse.create = function create(properties) {
            return new LoginResponse(properties);
        };

        /**
         * Encodes the specified LoginResponse message. Does not implicitly {@link example.LoginResponse.verify|verify} messages.
         * @function encode
         * @memberof example.LoginResponse
         * @static
         * @param {example.ILoginResponse} message LoginResponse message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        LoginResponse.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.result != null && message.hasOwnProperty("result"))
                writer.uint32(/* id 1, wireType 0 =*/8).bool(message.result);
            return writer;
        };

        /**
         * Encodes the specified LoginResponse message, length delimited. Does not implicitly {@link example.LoginResponse.verify|verify} messages.
         * @function encodeDelimited
         * @memberof example.LoginResponse
         * @static
         * @param {example.ILoginResponse} message LoginResponse message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        LoginResponse.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a LoginResponse message from the specified reader or buffer.
         * @function decode
         * @memberof example.LoginResponse
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {example.LoginResponse} LoginResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        LoginResponse.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.example.LoginResponse();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1:
                    message.result = reader.bool();
                    break;
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a LoginResponse message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof example.LoginResponse
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {example.LoginResponse} LoginResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        LoginResponse.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a LoginResponse message.
         * @function verify
         * @memberof example.LoginResponse
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        LoginResponse.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.result != null && message.hasOwnProperty("result"))
                if (typeof message.result !== "boolean")
                    return "result: boolean expected";
            return null;
        };

        /**
         * Creates a LoginResponse message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof example.LoginResponse
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {example.LoginResponse} LoginResponse
         */
        LoginResponse.fromObject = function fromObject(object) {
            if (object instanceof $root.example.LoginResponse)
                return object;
            var message = new $root.example.LoginResponse();
            if (object.result != null)
                message.result = Boolean(object.result);
            return message;
        };

        /**
         * Creates a plain object from a LoginResponse message. Also converts values to other types if specified.
         * @function toObject
         * @memberof example.LoginResponse
         * @static
         * @param {example.LoginResponse} message LoginResponse
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        LoginResponse.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults)
                object.result = false;
            if (message.result != null && message.hasOwnProperty("result"))
                object.result = message.result;
            return object;
        };

        /**
         * Converts this LoginResponse to JSON.
         * @function toJSON
         * @memberof example.LoginResponse
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        LoginResponse.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        return LoginResponse;
    })();

    example.Message = (function() {

        /**
         * Properties of a Message.
         * @memberof example
         * @interface IMessage
         * @property {string|null} [content] Message content
         */

        /**
         * Constructs a new Message.
         * @memberof example
         * @classdesc Represents a Message.
         * @implements IMessage
         * @constructor
         * @param {example.IMessage=} [properties] Properties to set
         */
        function Message(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * Message content.
         * @member {string} content
         * @memberof example.Message
         * @instance
         */
        Message.prototype.content = "";

        /**
         * Creates a new Message instance using the specified properties.
         * @function create
         * @memberof example.Message
         * @static
         * @param {example.IMessage=} [properties] Properties to set
         * @returns {example.Message} Message instance
         */
        Message.create = function create(properties) {
            return new Message(properties);
        };

        /**
         * Encodes the specified Message message. Does not implicitly {@link example.Message.verify|verify} messages.
         * @function encode
         * @memberof example.Message
         * @static
         * @param {example.IMessage} message Message message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        Message.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.content != null && message.hasOwnProperty("content"))
                writer.uint32(/* id 1, wireType 2 =*/10).string(message.content);
            return writer;
        };

        /**
         * Encodes the specified Message message, length delimited. Does not implicitly {@link example.Message.verify|verify} messages.
         * @function encodeDelimited
         * @memberof example.Message
         * @static
         * @param {example.IMessage} message Message message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        Message.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a Message message from the specified reader or buffer.
         * @function decode
         * @memberof example.Message
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {example.Message} Message
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        Message.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.example.Message();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1:
                    message.content = reader.string();
                    break;
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a Message message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof example.Message
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {example.Message} Message
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        Message.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a Message message.
         * @function verify
         * @memberof example.Message
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        Message.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.content != null && message.hasOwnProperty("content"))
                if (!$util.isString(message.content))
                    return "content: string expected";
            return null;
        };

        /**
         * Creates a Message message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof example.Message
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {example.Message} Message
         */
        Message.fromObject = function fromObject(object) {
            if (object instanceof $root.example.Message)
                return object;
            var message = new $root.example.Message();
            if (object.content != null)
                message.content = String(object.content);
            return message;
        };

        /**
         * Creates a plain object from a Message message. Also converts values to other types if specified.
         * @function toObject
         * @memberof example.Message
         * @static
         * @param {example.Message} message Message
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        Message.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults)
                object.content = "";
            if (message.content != null && message.hasOwnProperty("content"))
                object.content = message.content;
            return object;
        };

        /**
         * Converts this Message to JSON.
         * @function toJSON
         * @memberof example.Message
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        Message.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        return Message;
    })();

    return example;
})();

module.exports = $root;
