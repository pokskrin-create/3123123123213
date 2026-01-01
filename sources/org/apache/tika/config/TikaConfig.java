package org.apache.tika.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.tika.concurrent.ConfigurableThreadPoolExecutor;
import org.apache.tika.concurrent.SimpleThreadPoolExecutor;
import org.apache.tika.detect.CompositeDetector;
import org.apache.tika.detect.CompositeEncodingDetector;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.DefaultEncodingDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.detect.EncodingDetector;
import org.apache.tika.exception.TikaConfigException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.translate.DefaultTranslator;
import org.apache.tika.language.translate.Translator;
import org.apache.tika.metadata.filter.MetadataFilter;
import org.apache.tika.metadata.filter.NoOpFilter;
import org.apache.tika.metadata.listfilter.MetadataListFilter;
import org.apache.tika.metadata.listfilter.NoOpListFilter;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MediaTypeRegistry;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.mime.MimeTypesFactory;
import org.apache.tika.parser.AbstractEncodingDetectorParser;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.AutoDetectParserConfig;
import org.apache.tika.parser.CompositeParser;
import org.apache.tika.parser.DefaultParser;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ParserDecorator;
import org.apache.tika.parser.RenderingParser;
import org.apache.tika.parser.external.ExternalParsersConfigReaderMetKeys;
import org.apache.tika.parser.multiple.AbstractMultipleParser;
import org.apache.tika.renderer.CompositeRenderer;
import org.apache.tika.renderer.Renderer;
import org.apache.tika.utils.StringUtils;
import org.apache.tika.utils.XMLReaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class TikaConfig {
    public static int DEFAULT_MAX_JSON_STRING_FIELD_LENGTH = 20000000;
    private static int MAX_JSON_STRING_FIELD_LENGTH = 0;
    public static String MAX_JSON_STRING_FIELD_LENGTH_ELEMENT_NAME = "maxJsonStringFieldLength";
    private static final Map<String, InitializableProblemHandler> strategyMap;
    private final AutoDetectParserConfig autoDetectParserConfig;
    private final CompositeDetector detector;
    private final EncodingDetector encodingDetector;
    private final ExecutorService executorService;
    private final MetadataFilter metadataFilter;
    private final MetadataListFilter metadataListFilter;
    private final MimeTypes mimeTypes;
    private final CompositeParser parser;
    private final Renderer renderer;
    private final ServiceLoader serviceLoader;
    private final Translator translator;
    protected static final AtomicInteger TIMES_INSTANTIATED = new AtomicInteger();
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) TikaConfig.class);

    static {
        HashMap map = new HashMap();
        strategyMap = map;
        map.put("", InitializableProblemHandler.DEFAULT);
        map.put(InitializableProblemHandler.IGNORE.toString(), InitializableProblemHandler.IGNORE);
        map.put(InitializableProblemHandler.INFO.toString(), InitializableProblemHandler.INFO);
        map.put(InitializableProblemHandler.WARN.toString(), InitializableProblemHandler.WARN);
        map.put(InitializableProblemHandler.THROW.toString(), InitializableProblemHandler.THROW);
        MAX_JSON_STRING_FIELD_LENGTH = DEFAULT_MAX_JSON_STRING_FIELD_LENGTH;
    }

    public TikaConfig(String str) throws TikaException, SAXException, IOException {
        this(Paths.get(str, new String[0]));
    }

    public TikaConfig(Path path) throws TikaException, SAXException, IOException {
        this(XMLReaderUtils.buildDOM(path));
    }

    public TikaConfig(Path path, ServiceLoader serviceLoader) throws TikaException, SAXException, IOException {
        this(XMLReaderUtils.buildDOM(path), serviceLoader);
    }

    public TikaConfig(File file) throws TikaException, SAXException, IOException {
        this(XMLReaderUtils.buildDOM(file.toPath()));
    }

    public TikaConfig(File file, ServiceLoader serviceLoader) throws TikaException, SAXException, IOException {
        this(XMLReaderUtils.buildDOM(file.toPath()), serviceLoader);
    }

    public TikaConfig(URL url) throws TikaException, SAXException, IOException {
        this(url, ServiceLoader.getContextClassLoader());
    }

    public TikaConfig(URL url, ClassLoader classLoader) throws TikaException, SAXException, IOException {
        this(XMLReaderUtils.buildDOM(url.toString()).getDocumentElement(), classLoader);
    }

    public TikaConfig(URL url, ServiceLoader serviceLoader) throws TikaException, SAXException, IOException {
        this(XMLReaderUtils.buildDOM(url.toString()).getDocumentElement(), serviceLoader);
    }

    public TikaConfig(InputStream inputStream) throws TikaException, SAXException, IOException {
        this(XMLReaderUtils.buildDOM(inputStream));
    }

    public TikaConfig(Document document) throws TikaException, IOException {
        this(document.getDocumentElement());
    }

    public TikaConfig(Document document, ServiceLoader serviceLoader) throws TikaException, IOException {
        this(document.getDocumentElement(), serviceLoader);
    }

    public TikaConfig(Element element) throws TikaException, IOException {
        this(element, serviceLoaderFromDomElement(element, null));
    }

    public TikaConfig(Element element, ClassLoader classLoader) throws TikaException, IOException {
        this(element, serviceLoaderFromDomElement(element, classLoader));
    }

    private TikaConfig(Element element, ServiceLoader serviceLoader) throws TikaException, IOException {
        DetectorXmlLoader detectorXmlLoader = new DetectorXmlLoader();
        TranslatorXmlLoader translatorXmlLoader = new TranslatorXmlLoader();
        ExecutorServiceXmlLoader executorServiceXmlLoader = new ExecutorServiceXmlLoader();
        EncodingDetectorXmlLoader encodingDetectorXmlLoader = new EncodingDetectorXmlLoader();
        RendererXmlLoader rendererXmlLoader = new RendererXmlLoader();
        updateXMLReaderUtils(element);
        MimeTypes mimeTypesTypesFromDomElement = typesFromDomElement(element);
        this.mimeTypes = mimeTypesTypesFromDomElement;
        this.detector = detectorXmlLoader.loadOverall(element, mimeTypesTypesFromDomElement, serviceLoader);
        EncodingDetector encodingDetectorLoadOverall = encodingDetectorXmlLoader.loadOverall(element, mimeTypesTypesFromDomElement, serviceLoader);
        this.encodingDetector = encodingDetectorLoadOverall;
        Renderer rendererLoadOverall = rendererXmlLoader.loadOverall(element, mimeTypesTypesFromDomElement, serviceLoader);
        this.renderer = rendererLoadOverall;
        this.parser = new ParserXmlLoader(encodingDetectorLoadOverall, rendererLoadOverall).loadOverall(element, mimeTypesTypesFromDomElement, serviceLoader);
        this.translator = translatorXmlLoader.loadOverall(element, mimeTypesTypesFromDomElement, serviceLoader);
        this.executorService = executorServiceXmlLoader.loadOverall(element, mimeTypesTypesFromDomElement, serviceLoader);
        this.metadataFilter = MetadataFilter.load(element, true);
        this.metadataListFilter = MetadataListFilter.load(element, true);
        this.autoDetectParserConfig = AutoDetectParserConfig.load(element);
        this.serviceLoader = serviceLoader;
        setMaxJsonStringFieldLength(element);
        TIMES_INSTANTIATED.incrementAndGet();
    }

    public TikaConfig(ClassLoader classLoader) throws IOException, MimeTypeException {
        ServiceLoader serviceLoader = new ServiceLoader(classLoader);
        this.serviceLoader = serviceLoader;
        MimeTypes defaultMimeTypes = getDefaultMimeTypes(classLoader);
        this.mimeTypes = defaultMimeTypes;
        this.detector = getDefaultDetector(defaultMimeTypes, serviceLoader);
        CompositeEncodingDetector defaultEncodingDetector = getDefaultEncodingDetector(serviceLoader);
        this.encodingDetector = defaultEncodingDetector;
        CompositeRenderer defaultRenderer = getDefaultRenderer(serviceLoader);
        this.renderer = defaultRenderer;
        this.parser = getDefaultParser(defaultMimeTypes, serviceLoader, defaultEncodingDetector, defaultRenderer);
        this.translator = getDefaultTranslator(serviceLoader);
        this.executorService = getDefaultExecutorService();
        this.metadataFilter = new NoOpFilter();
        this.metadataListFilter = new NoOpListFilter();
        this.autoDetectParserConfig = AutoDetectParserConfig.DEFAULT;
        TIMES_INSTANTIATED.incrementAndGet();
    }

    public TikaConfig() throws TikaException, IOException {
        String property = System.getProperty("tika.config");
        if (!StringUtils.isBlank(property)) {
            LOG.debug("loading tika config from system property 'tika.config'");
        }
        if (StringUtils.isBlank(property)) {
            property = System.getenv("TIKA_CONFIG");
            if (!StringUtils.isBlank(property)) {
                LOG.debug("loading tika config from environment variable 'TIKA_CONFIG'");
            }
        }
        if (StringUtils.isBlank(property)) {
            LOG.debug("loading tika config from defaults; no config file specified");
            ServiceLoader serviceLoader = new ServiceLoader();
            this.serviceLoader = serviceLoader;
            MimeTypes defaultMimeTypes = getDefaultMimeTypes(ServiceLoader.getContextClassLoader());
            this.mimeTypes = defaultMimeTypes;
            CompositeEncodingDetector defaultEncodingDetector = getDefaultEncodingDetector(serviceLoader);
            this.encodingDetector = defaultEncodingDetector;
            CompositeRenderer defaultRenderer = getDefaultRenderer(serviceLoader);
            this.renderer = defaultRenderer;
            this.parser = getDefaultParser(defaultMimeTypes, serviceLoader, defaultEncodingDetector, defaultRenderer);
            this.detector = getDefaultDetector(defaultMimeTypes, serviceLoader);
            this.translator = getDefaultTranslator(serviceLoader);
            this.executorService = getDefaultExecutorService();
            this.metadataFilter = new NoOpFilter();
            this.metadataListFilter = new NoOpListFilter();
            this.autoDetectParserConfig = AutoDetectParserConfig.DEFAULT;
        } else {
            ServiceLoader serviceLoader2 = new ServiceLoader();
            LOG.debug("loading tika config from: " + property);
            try {
                InputStream configInputStream = getConfigInputStream(property, serviceLoader2);
                try {
                    Element documentElement = XMLReaderUtils.buildDOM(configInputStream).getDocumentElement();
                    updateXMLReaderUtils(documentElement);
                    ServiceLoader serviceLoaderServiceLoaderFromDomElement = serviceLoaderFromDomElement(documentElement, serviceLoader2.getLoader());
                    this.serviceLoader = serviceLoaderServiceLoaderFromDomElement;
                    DetectorXmlLoader detectorXmlLoader = new DetectorXmlLoader();
                    EncodingDetectorXmlLoader encodingDetectorXmlLoader = new EncodingDetectorXmlLoader();
                    RendererXmlLoader rendererXmlLoader = new RendererXmlLoader();
                    TranslatorXmlLoader translatorXmlLoader = new TranslatorXmlLoader();
                    ExecutorServiceXmlLoader executorServiceXmlLoader = new ExecutorServiceXmlLoader();
                    MimeTypes mimeTypesTypesFromDomElement = typesFromDomElement(documentElement);
                    this.mimeTypes = mimeTypesTypesFromDomElement;
                    EncodingDetector encodingDetectorLoadOverall = encodingDetectorXmlLoader.loadOverall(documentElement, mimeTypesTypesFromDomElement, serviceLoaderServiceLoaderFromDomElement);
                    this.encodingDetector = encodingDetectorLoadOverall;
                    Renderer rendererLoadOverall = rendererXmlLoader.loadOverall(documentElement, mimeTypesTypesFromDomElement, serviceLoaderServiceLoaderFromDomElement);
                    this.renderer = rendererLoadOverall;
                    this.parser = new ParserXmlLoader(encodingDetectorLoadOverall, rendererLoadOverall).loadOverall(documentElement, mimeTypesTypesFromDomElement, serviceLoaderServiceLoaderFromDomElement);
                    this.detector = detectorXmlLoader.loadOverall(documentElement, mimeTypesTypesFromDomElement, serviceLoaderServiceLoaderFromDomElement);
                    this.translator = translatorXmlLoader.loadOverall(documentElement, mimeTypesTypesFromDomElement, serviceLoaderServiceLoaderFromDomElement);
                    this.executorService = executorServiceXmlLoader.loadOverall(documentElement, mimeTypesTypesFromDomElement, serviceLoaderServiceLoaderFromDomElement);
                    this.metadataFilter = MetadataFilter.load(documentElement, true);
                    this.metadataListFilter = MetadataListFilter.load(documentElement, true);
                    this.autoDetectParserConfig = AutoDetectParserConfig.load(documentElement);
                    setMaxJsonStringFieldLength(documentElement);
                    if (configInputStream != null) {
                        configInputStream.close();
                    }
                } finally {
                }
            } catch (SAXException e) {
                throw new TikaException("Specified Tika configuration has syntax errors: " + property, e);
            }
        }
        TIMES_INSTANTIATED.incrementAndGet();
    }

    public static int getMaxJsonStringFieldLength() {
        return MAX_JSON_STRING_FIELD_LENGTH;
    }

    private void setMaxJsonStringFieldLength(Element element) throws TikaConfigException {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node nodeItem = childNodes.item(i);
            if (nodeItem.getNodeName().equals(MAX_JSON_STRING_FIELD_LENGTH_ELEMENT_NAME)) {
                try {
                    MAX_JSON_STRING_FIELD_LENGTH = Integer.parseInt(nodeItem.getTextContent());
                    return;
                } catch (NumberFormatException e) {
                    throw new TikaConfigException(MAX_JSON_STRING_FIELD_LENGTH_ELEMENT_NAME + " is not an integer", e);
                }
            }
        }
    }

    private static MimeTypes getDefaultMimeTypes(ClassLoader classLoader) {
        return MimeTypes.getDefaultMimeTypes(classLoader);
    }

    protected static CompositeDetector getDefaultDetector(MimeTypes mimeTypes, ServiceLoader serviceLoader) {
        return new DefaultDetector(mimeTypes, serviceLoader);
    }

    protected static CompositeEncodingDetector getDefaultEncodingDetector(ServiceLoader serviceLoader) {
        return new DefaultEncodingDetector(serviceLoader);
    }

    protected static CompositeRenderer getDefaultRenderer(ServiceLoader serviceLoader) {
        return new CompositeRenderer(serviceLoader);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static CompositeParser getDefaultParser(MimeTypes mimeTypes, ServiceLoader serviceLoader, EncodingDetector encodingDetector, Renderer renderer) {
        return new DefaultParser(mimeTypes.getMediaTypeRegistry(), serviceLoader, encodingDetector, renderer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Translator getDefaultTranslator(ServiceLoader serviceLoader) {
        return new DefaultTranslator(serviceLoader);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ConfigurableThreadPoolExecutor getDefaultExecutorService() {
        return new SimpleThreadPoolExecutor();
    }

    private static InputStream getConfigInputStream(String str, ServiceLoader serviceLoader) throws TikaException, IOException {
        InputStream inputStreamNewInputStream;
        try {
            inputStreamNewInputStream = new URI(str).toURL().openStream();
        } catch (IOException | URISyntaxException unused) {
            inputStreamNewInputStream = null;
        }
        if (inputStreamNewInputStream == null) {
            inputStreamNewInputStream = serviceLoader.getResourceAsStream(str);
        }
        if (inputStreamNewInputStream == null) {
            Path path = Paths.get(str, new String[0]);
            if (Files.isRegularFile(path, new LinkOption[0])) {
                inputStreamNewInputStream = Files.newInputStream(path, new OpenOption[0]);
            }
        }
        if (inputStreamNewInputStream != null) {
            return inputStreamNewInputStream;
        }
        throw new TikaException("Specified Tika configuration not found: " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getText(Node node) {
        short nodeType = node.getNodeType();
        if (nodeType != 1) {
            if (nodeType == 3) {
                return node.getNodeValue();
            }
            return "";
        }
        StringBuilder sb = new StringBuilder();
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            sb.append(getText(childNodes.item(i)));
        }
        return sb.toString();
    }

    public static TikaConfig getDefaultConfig() {
        try {
            return new TikaConfig();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read default configuration", e);
        } catch (TikaException e2) {
            throw new RuntimeException("Unable to access default configuration", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Element getChild(Element element, String str) {
        for (Node firstChild = element.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild.getNodeType() == 1 && str.equals(firstChild.getNodeName())) {
                return (Element) firstChild;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<Element> getTopLevelElementChildren(Element element, String str, String str2) throws TikaException {
        Node nodeItem = element;
        if (str != null) {
            NodeList elementsByTagName = element.getElementsByTagName(str);
            if (elementsByTagName.getLength() > 1) {
                throw new TikaException("Properties may not contain multiple " + str + " entries");
            }
            nodeItem = elementsByTagName.getLength() == 1 ? elementsByTagName.item(0) : null;
        }
        if (nodeItem != null) {
            NodeList childNodes = nodeItem.getChildNodes();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node nodeItem2 = childNodes.item(i);
                if (nodeItem2 instanceof Element) {
                    Element element2 = (Element) nodeItem2;
                    if (str2.equals(element2.getTagName())) {
                        arrayList.add(element2);
                    }
                }
            }
            return arrayList;
        }
        return Collections.EMPTY_LIST;
    }

    private static MimeTypes typesFromDomElement(Element element) throws TikaException, IOException {
        Element child = getChild(element, "mimeTypeRepository");
        if (child != null && child.hasAttribute("resource")) {
            return MimeTypesFactory.create(child.getAttribute("resource"));
        }
        return getDefaultMimeTypes(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Set<MediaType> mediaTypesListFromDomElement(Element element, String str) throws TikaException {
        NodeList childNodes = element.getChildNodes();
        HashSet hashSet = null;
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node nodeItem = childNodes.item(i);
            if (nodeItem instanceof Element) {
                Element element2 = (Element) nodeItem;
                if (str.equals(element2.getTagName())) {
                    String text = getText(element2);
                    MediaType mediaType = MediaType.parse(text);
                    if (mediaType != null) {
                        if (hashSet == null) {
                            hashSet = new HashSet();
                        }
                        hashSet.add(mediaType);
                    } else {
                        throw new TikaException("Invalid media type name: " + text);
                    }
                } else {
                    continue;
                }
            }
        }
        return hashSet != null ? hashSet : Collections.EMPTY_SET;
    }

    private static ServiceLoader serviceLoaderFromDomElement(Element element, ClassLoader classLoader) throws TikaConfigException {
        Element child = getChild(element, "service-loader");
        if (child == null) {
            if (classLoader != null) {
                return new ServiceLoader(classLoader);
            }
            return new ServiceLoader();
        }
        boolean z = Boolean.parseBoolean(child.getAttribute("dynamic"));
        LoadErrorHandler loadErrorHandler = LoadErrorHandler.THROW;
        String attribute = child.getAttribute("loadErrorHandler");
        if (LoadErrorHandler.WARN.toString().equalsIgnoreCase(attribute)) {
            loadErrorHandler = LoadErrorHandler.WARN;
        } else if (LoadErrorHandler.THROW.toString().equalsIgnoreCase(attribute)) {
            loadErrorHandler = LoadErrorHandler.THROW;
        } else if (LoadErrorHandler.IGNORE.toString().equalsIgnoreCase(attribute)) {
            loadErrorHandler = LoadErrorHandler.IGNORE;
        }
        InitializableProblemHandler initializableProblemHandler = getInitializableProblemHandler(child.getAttribute("initializableProblemHandler"));
        if (classLoader == null) {
            classLoader = ServiceLoader.getContextClassLoader();
        }
        return new ServiceLoader(classLoader, loadErrorHandler, initializableProblemHandler, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static InitializableProblemHandler getInitializableProblemHandler(String str) throws TikaConfigException {
        InitializableProblemHandler initializableProblemHandler = strategyMap.get(str.toUpperCase(Locale.US));
        if (initializableProblemHandler != null) {
            return initializableProblemHandler;
        }
        throw new TikaConfigException(String.format(Locale.US, "Couldn't parse non-null '%s'. Must be one of 'ignore', 'info', 'warn' or 'throw'", str));
    }

    public static void mustNotBeEmpty(String str, String str2) throws TikaConfigException {
        if (StringUtils.isBlank(str2)) {
            throw new IllegalArgumentException("parameter '" + str + "' must be set in the config file");
        }
    }

    public static void mustNotBeEmpty(String str, Path path) throws TikaConfigException {
        if (path != null) {
            return;
        }
        throw new IllegalArgumentException("parameter '" + str + "' must be set in the config file");
    }

    private void updateXMLReaderUtils(Element element) throws TikaException {
        Element child = getChild(element, "xml-reader-utils");
        if (child == null) {
            return;
        }
        if (child.hasAttribute("maxEntityExpansions")) {
            XMLReaderUtils.setMaxEntityExpansions(Integer.parseInt(child.getAttribute("maxEntityExpansions")));
        }
        if (child.hasAttribute("poolSize")) {
            XMLReaderUtils.setPoolSize(Integer.parseInt(child.getAttribute("poolSize")));
        }
    }

    public Parser getParser() {
        return this.parser;
    }

    public Detector getDetector() {
        return this.detector;
    }

    public EncodingDetector getEncodingDetector() {
        return this.encodingDetector;
    }

    public Translator getTranslator() {
        return this.translator;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public MimeTypes getMimeRepository() {
        return this.mimeTypes;
    }

    public MediaTypeRegistry getMediaTypeRegistry() {
        return this.mimeTypes.getMediaTypeRegistry();
    }

    public ServiceLoader getServiceLoader() {
        return this.serviceLoader;
    }

    public MetadataFilter getMetadataFilter() {
        return this.metadataFilter;
    }

    public MetadataListFilter getMetadataListFilter() {
        return this.metadataListFilter;
    }

    public AutoDetectParserConfig getAutoDetectParserConfig() {
        return this.autoDetectParserConfig;
    }

    private static abstract class XmlLoader<CT, T> {
        protected static final String PARAMS_TAG_NAME = "params";

        abstract T createComposite(Class<? extends T> cls, List<T> list, Set<Class<? extends T>> set, Map<String, Param> map, MimeTypes mimeTypes, ServiceLoader serviceLoader) throws IllegalAccessException, InstantiationException, InvocationTargetException;

        abstract CT createComposite(List<T> list, MimeTypes mimeTypes, ServiceLoader serviceLoader);

        abstract CT createDefault(MimeTypes mimeTypes, ServiceLoader serviceLoader);

        abstract T decorate(T t, Element element) throws TikaException, IOException;

        abstract Class<? extends T> getLoaderClass();

        abstract String getLoaderTagName();

        abstract String getParentTagName();

        abstract boolean isComposite(Class<? extends T> cls);

        abstract boolean isComposite(T t);

        abstract T preLoadOne(Class<? extends T> cls, String str, MimeTypes mimeTypes) throws TikaException;

        abstract boolean supportsComposite();

        private XmlLoader() {
        }

        CT loadOverall(Element element, MimeTypes mimeTypes, ServiceLoader serviceLoader) throws TikaException, IOException {
            List<T> arrayList = new ArrayList<>();
            Iterator it = TikaConfig.getTopLevelElementChildren(element, getParentTagName(), getLoaderTagName()).iterator();
            while (it.hasNext()) {
                T tLoadOne = loadOne((Element) it.next(), mimeTypes, serviceLoader);
                if (tLoadOne != null) {
                    arrayList.add(tLoadOne);
                }
            }
            if (arrayList.isEmpty()) {
                return createDefault(mimeTypes, serviceLoader);
            }
            if (arrayList.size() == 1) {
                CT ct = (CT) arrayList.get(0);
                if (isComposite((XmlLoader<CT, T>) ct)) {
                    return ct;
                }
            } else if (!supportsComposite()) {
                throw new TikaConfigException("Composite not supported for " + getParentTagName() + ". Must specify only one child!");
            }
            return createComposite(arrayList, mimeTypes, serviceLoader);
        }

        /* JADX WARN: Removed duplicated region for block: B:92:0x01b2  */
        /* JADX WARN: Removed duplicated region for block: B:94:0x01bb  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        T loadOne(org.w3c.dom.Element r13, org.apache.tika.mime.MimeTypes r14, org.apache.tika.config.ServiceLoader r15) throws org.apache.tika.exception.TikaException, java.io.IOException {
            /*
                Method dump skipped, instructions count: 494
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.tika.config.TikaConfig.XmlLoader.loadOne(org.w3c.dom.Element, org.apache.tika.mime.MimeTypes, org.apache.tika.config.ServiceLoader):java.lang.Object");
        }

        T newInstance(Class<? extends T> cls) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
            return cls.getDeclaredConstructor(null).newInstance(null);
        }

        Map<String, Param> getParams(Element element) throws IllegalAccessException, TikaException, NoSuchMethodException, DOMException, SecurityException, IllegalArgumentException, InvocationTargetException {
            HashMap map = new HashMap();
            Node firstChild = element.getFirstChild();
            while (true) {
                if (firstChild == null) {
                    break;
                }
                if (!PARAMS_TAG_NAME.equals(firstChild.getNodeName())) {
                    firstChild = firstChild.getNextSibling();
                } else if (firstChild.hasChildNodes()) {
                    NodeList childNodes = firstChild.getChildNodes();
                    for (int i = 0; i < childNodes.getLength(); i++) {
                        Node nodeItem = childNodes.item(i);
                        if (nodeItem.getNodeType() == 1) {
                            Param paramLoad = Param.load(nodeItem);
                            map.put(paramLoad.getName(), paramLoad);
                        }
                    }
                }
            }
            return map;
        }
    }

    private static class ParserXmlLoader extends XmlLoader<CompositeParser, Parser> {
        private final EncodingDetector encodingDetector;
        private final Renderer renderer;

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        boolean supportsComposite() {
            return true;
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        /* bridge */ /* synthetic */ Parser createComposite(Class<? extends Parser> cls, List<Parser> list, Set<Class<? extends Parser>> set, Map map, MimeTypes mimeTypes, ServiceLoader serviceLoader) throws IllegalAccessException, InstantiationException, InvocationTargetException {
            return createComposite2(cls, list, set, (Map<String, Param>) map, mimeTypes, serviceLoader);
        }

        private ParserXmlLoader(EncodingDetector encodingDetector, Renderer renderer) {
            super();
            this.encodingDetector = encodingDetector;
            this.renderer = renderer;
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        String getParentTagName() {
            return "parsers";
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        String getLoaderTagName() {
            return ExternalParsersConfigReaderMetKeys.PARSER_TAG;
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        Class<? extends Parser> getLoaderClass() {
            return Parser.class;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public Parser preLoadOne(Class<? extends Parser> cls, String str, MimeTypes mimeTypes) throws TikaException {
            if (!AutoDetectParser.class.isAssignableFrom(cls)) {
                return null;
            }
            throw new TikaException("AutoDetectParser not supported in a <parser> configuration element: " + str);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public boolean isComposite(Parser parser) {
            return parser instanceof CompositeParser;
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        boolean isComposite(Class<? extends Parser> cls) {
            return CompositeParser.class.isAssignableFrom(cls) || AbstractMultipleParser.class.isAssignableFrom(cls) || ParserDecorator.class.isAssignableFrom(cls);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public CompositeParser createDefault(MimeTypes mimeTypes, ServiceLoader serviceLoader) {
            return TikaConfig.getDefaultParser(mimeTypes, serviceLoader, this.encodingDetector, this.renderer);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public CompositeParser createComposite(List<Parser> list, MimeTypes mimeTypes, ServiceLoader serviceLoader) {
            return new CompositeParser(mimeTypes.getMediaTypeRegistry(), list);
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        /* renamed from: createComposite, reason: avoid collision after fix types in other method */
        Parser createComposite2(Class<? extends Parser> cls, List<Parser> list, Set<Class<? extends Parser>> set, Map<String, Param> map, MimeTypes mimeTypes, ServiceLoader serviceLoader) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
            Parser parserNewInstance;
            CompositeParser compositeParser;
            MediaTypeRegistry mediaTypeRegistry = mimeTypes.getMediaTypeRegistry();
            try {
                parserNewInstance = cls.getConstructor(MediaTypeRegistry.class, ServiceLoader.class, Collection.class, EncodingDetector.class, Renderer.class).newInstance(mediaTypeRegistry, serviceLoader, set, this.encodingDetector, this.renderer);
            } catch (NoSuchMethodException unused) {
                parserNewInstance = null;
            }
            if (parserNewInstance == null) {
                try {
                    parserNewInstance = cls.getConstructor(MediaTypeRegistry.class, ServiceLoader.class, Collection.class, EncodingDetector.class).newInstance(mediaTypeRegistry, serviceLoader, set, this.encodingDetector);
                } catch (NoSuchMethodException unused2) {
                }
            }
            if (parserNewInstance == null) {
                try {
                    parserNewInstance = cls.getConstructor(MediaTypeRegistry.class, ServiceLoader.class, Collection.class).newInstance(mediaTypeRegistry, serviceLoader, set);
                } catch (NoSuchMethodException unused3) {
                }
            }
            if (parserNewInstance == null) {
                try {
                    parserNewInstance = cls.getConstructor(MediaTypeRegistry.class, List.class, Collection.class).newInstance(mediaTypeRegistry, list, set);
                } catch (NoSuchMethodException unused4) {
                }
            }
            if (parserNewInstance == null) {
                try {
                    parserNewInstance = cls.getConstructor(MediaTypeRegistry.class, Collection.class, Map.class).newInstance(mediaTypeRegistry, list, map);
                } catch (NoSuchMethodException unused5) {
                }
            }
            if (parserNewInstance == null) {
                try {
                    parserNewInstance = cls.getConstructor(MediaTypeRegistry.class, List.class).newInstance(mediaTypeRegistry, list);
                } catch (NoSuchMethodException unused6) {
                }
            }
            if (parserNewInstance != null || !ParserDecorator.class.isAssignableFrom(cls)) {
                return parserNewInstance;
            }
            try {
                if (list.size() == 1 && set.isEmpty() && (list.get(0) instanceof CompositeParser)) {
                    compositeParser = (CompositeParser) list.get(0);
                } else {
                    compositeParser = new CompositeParser(mediaTypeRegistry, list, set);
                }
                return cls.getConstructor(Parser.class).newInstance(compositeParser);
            } catch (NoSuchMethodException unused7) {
                return parserNewInstance;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public Parser newInstance(Class<? extends Parser> cls) throws IllegalAccessException, NoSuchMethodException, InstantiationException, IllegalArgumentException, InvocationTargetException {
            Parser parserNewInstance;
            if (AbstractEncodingDetectorParser.class.isAssignableFrom(cls)) {
                parserNewInstance = cls.getConstructor(EncodingDetector.class).newInstance(this.encodingDetector);
            } else {
                parserNewInstance = cls.getDeclaredConstructor(null).newInstance(null);
            }
            if (parserNewInstance instanceof RenderingParser) {
                ((RenderingParser) parserNewInstance).setRenderer(this.renderer);
            }
            return parserNewInstance;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public Parser decorate(Parser parser, Element element) throws TikaException {
            Set setMediaTypesListFromDomElement = TikaConfig.mediaTypesListFromDomElement(element, "mime");
            if (!setMediaTypesListFromDomElement.isEmpty()) {
                parser = ParserDecorator.withTypes(parser, setMediaTypesListFromDomElement);
            }
            Set setMediaTypesListFromDomElement2 = TikaConfig.mediaTypesListFromDomElement(element, "mime-exclude");
            return !setMediaTypesListFromDomElement2.isEmpty() ? ParserDecorator.withoutTypes(parser, setMediaTypesListFromDomElement2) : parser;
        }
    }

    private static class DetectorXmlLoader extends XmlLoader<CompositeDetector, Detector> {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public Detector decorate(Detector detector, Element element) {
            return detector;
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        boolean supportsComposite() {
            return true;
        }

        private DetectorXmlLoader() {
            super();
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        /* bridge */ /* synthetic */ Detector createComposite(Class<? extends Detector> cls, List<Detector> list, Set<Class<? extends Detector>> set, Map map, MimeTypes mimeTypes, ServiceLoader serviceLoader) throws IllegalAccessException, InstantiationException, InvocationTargetException {
            return createComposite2(cls, list, set, (Map<String, Param>) map, mimeTypes, serviceLoader);
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        String getParentTagName() {
            return "detectors";
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        String getLoaderTagName() {
            return "detector";
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        Class<? extends Detector> getLoaderClass() {
            return Detector.class;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public Detector preLoadOne(Class<? extends Detector> cls, String str, MimeTypes mimeTypes) {
            if (MimeTypes.class.equals(cls)) {
                return mimeTypes;
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public boolean isComposite(Detector detector) {
            return detector instanceof CompositeDetector;
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        boolean isComposite(Class<? extends Detector> cls) {
            return CompositeDetector.class.isAssignableFrom(cls);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public CompositeDetector createDefault(MimeTypes mimeTypes, ServiceLoader serviceLoader) {
            return TikaConfig.getDefaultDetector(mimeTypes, serviceLoader);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public CompositeDetector createComposite(List<Detector> list, MimeTypes mimeTypes, ServiceLoader serviceLoader) {
            return new CompositeDetector(mimeTypes.getMediaTypeRegistry(), list);
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        /* renamed from: createComposite, reason: avoid collision after fix types in other method */
        Detector createComposite2(Class<? extends Detector> cls, List<Detector> list, Set<Class<? extends Detector>> set, Map<String, Param> map, MimeTypes mimeTypes, ServiceLoader serviceLoader) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
            Detector detectorNewInstance;
            MediaTypeRegistry mediaTypeRegistry = mimeTypes.getMediaTypeRegistry();
            try {
                detectorNewInstance = cls.getConstructor(MimeTypes.class, ServiceLoader.class, Collection.class).newInstance(mimeTypes, serviceLoader, set);
            } catch (NoSuchMethodException unused) {
                detectorNewInstance = null;
            }
            if (detectorNewInstance == null) {
                try {
                    detectorNewInstance = cls.getConstructor(MediaTypeRegistry.class, List.class, Collection.class).newInstance(mediaTypeRegistry, list, set);
                } catch (NoSuchMethodException unused2) {
                }
            }
            if (detectorNewInstance == null) {
                try {
                    detectorNewInstance = cls.getConstructor(MediaTypeRegistry.class, List.class).newInstance(mediaTypeRegistry, list);
                } catch (NoSuchMethodException unused3) {
                }
            }
            if (detectorNewInstance != null) {
                return detectorNewInstance;
            }
            try {
                return cls.getConstructor(List.class).newInstance(list);
            } catch (NoSuchMethodException unused4) {
                return detectorNewInstance;
            }
        }
    }

    private static class TranslatorXmlLoader extends XmlLoader<Translator, Translator> {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public Translator decorate(Translator translator, Element element) {
            return translator;
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        String getParentTagName() {
            return null;
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        boolean isComposite(Class<? extends Translator> cls) {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public boolean isComposite(Translator translator) {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public Translator preLoadOne(Class<? extends Translator> cls, String str, MimeTypes mimeTypes) {
            return null;
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        boolean supportsComposite() {
            return false;
        }

        private TranslatorXmlLoader() {
            super();
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        /* bridge */ /* synthetic */ Translator createComposite(Class<? extends Translator> cls, List<Translator> list, Set<Class<? extends Translator>> set, Map map, MimeTypes mimeTypes, ServiceLoader serviceLoader) throws IllegalAccessException, InstantiationException, InvocationTargetException {
            return createComposite2(cls, list, set, (Map<String, Param>) map, mimeTypes, serviceLoader);
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        String getLoaderTagName() {
            return "translator";
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        Class<? extends Translator> getLoaderClass() {
            return Translator.class;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public Translator createDefault(MimeTypes mimeTypes, ServiceLoader serviceLoader) {
            return TikaConfig.getDefaultTranslator(serviceLoader);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public Translator createComposite(List<Translator> list, MimeTypes mimeTypes, ServiceLoader serviceLoader) {
            return list.get(0);
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        /* renamed from: createComposite, reason: avoid collision after fix types in other method */
        Translator createComposite2(Class<? extends Translator> cls, List<Translator> list, Set<Class<? extends Translator>> set, Map<String, Param> map, MimeTypes mimeTypes, ServiceLoader serviceLoader) throws InstantiationException {
            throw new InstantiationException("Only one translator supported");
        }
    }

    private static class ExecutorServiceXmlLoader extends XmlLoader<ConfigurableThreadPoolExecutor, ConfigurableThreadPoolExecutor> {
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        String getParentTagName() {
            return null;
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        boolean isComposite(Class<? extends ConfigurableThreadPoolExecutor> cls) {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public boolean isComposite(ConfigurableThreadPoolExecutor configurableThreadPoolExecutor) {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public ConfigurableThreadPoolExecutor preLoadOne(Class<? extends ConfigurableThreadPoolExecutor> cls, String str, MimeTypes mimeTypes) {
            return null;
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        boolean supportsComposite() {
            return false;
        }

        private ExecutorServiceXmlLoader() {
            super();
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        /* bridge */ /* synthetic */ ConfigurableThreadPoolExecutor createComposite(Class<? extends ConfigurableThreadPoolExecutor> cls, List<ConfigurableThreadPoolExecutor> list, Set<Class<? extends ConfigurableThreadPoolExecutor>> set, Map map, MimeTypes mimeTypes, ServiceLoader serviceLoader) throws IllegalAccessException, InstantiationException, InvocationTargetException {
            return createComposite2(cls, list, set, (Map<String, Param>) map, mimeTypes, serviceLoader);
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        /* renamed from: createComposite, reason: avoid collision after fix types in other method */
        ConfigurableThreadPoolExecutor createComposite2(Class<? extends ConfigurableThreadPoolExecutor> cls, List<ConfigurableThreadPoolExecutor> list, Set<Class<? extends ConfigurableThreadPoolExecutor>> set, Map<String, Param> map, MimeTypes mimeTypes, ServiceLoader serviceLoader) throws InstantiationException {
            throw new InstantiationException("Only one executor service supported");
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public ConfigurableThreadPoolExecutor createComposite(List<ConfigurableThreadPoolExecutor> list, MimeTypes mimeTypes, ServiceLoader serviceLoader) {
            return list.get(0);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public ConfigurableThreadPoolExecutor createDefault(MimeTypes mimeTypes, ServiceLoader serviceLoader) {
            return TikaConfig.getDefaultExecutorService();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public ConfigurableThreadPoolExecutor decorate(ConfigurableThreadPoolExecutor configurableThreadPoolExecutor, Element element) {
            Element child = TikaConfig.getChild(element, "max-threads");
            if (child != null) {
                configurableThreadPoolExecutor.setMaximumPoolSize(Integer.parseInt(TikaConfig.getText(child)));
            }
            Element child2 = TikaConfig.getChild(element, "core-threads");
            if (child2 != null) {
                configurableThreadPoolExecutor.setCorePoolSize(Integer.parseInt(TikaConfig.getText(child2)));
            }
            return configurableThreadPoolExecutor;
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        Class<? extends ConfigurableThreadPoolExecutor> getLoaderClass() {
            return ConfigurableThreadPoolExecutor.class;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public ConfigurableThreadPoolExecutor loadOne(Element element, MimeTypes mimeTypes, ServiceLoader serviceLoader) throws TikaException, IOException {
            return (ConfigurableThreadPoolExecutor) super.loadOne(element, mimeTypes, serviceLoader);
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        String getLoaderTagName() {
            return "executor-service";
        }
    }

    private static class EncodingDetectorXmlLoader extends XmlLoader<EncodingDetector, EncodingDetector> {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public EncodingDetector decorate(EncodingDetector encodingDetector, Element element) {
            return encodingDetector;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public EncodingDetector preLoadOne(Class<? extends EncodingDetector> cls, String str, MimeTypes mimeTypes) {
            return null;
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        boolean supportsComposite() {
            return true;
        }

        private EncodingDetectorXmlLoader() {
            super();
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        /* bridge */ /* synthetic */ EncodingDetector createComposite(Class<? extends EncodingDetector> cls, List<EncodingDetector> list, Set<Class<? extends EncodingDetector>> set, Map map, MimeTypes mimeTypes, ServiceLoader serviceLoader) throws IllegalAccessException, InstantiationException, InvocationTargetException {
            return createComposite2(cls, list, set, (Map<String, Param>) map, mimeTypes, serviceLoader);
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        String getParentTagName() {
            return "encodingDetectors";
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        String getLoaderTagName() {
            return "encodingDetector";
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        Class<? extends EncodingDetector> getLoaderClass() {
            return EncodingDetector.class;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public boolean isComposite(EncodingDetector encodingDetector) {
            return encodingDetector instanceof CompositeEncodingDetector;
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        boolean isComposite(Class<? extends EncodingDetector> cls) {
            return CompositeEncodingDetector.class.isAssignableFrom(cls);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public EncodingDetector createDefault(MimeTypes mimeTypes, ServiceLoader serviceLoader) {
            return TikaConfig.getDefaultEncodingDetector(serviceLoader);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public EncodingDetector createComposite(List<EncodingDetector> list, MimeTypes mimeTypes, ServiceLoader serviceLoader) {
            return new CompositeEncodingDetector(list);
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        /* renamed from: createComposite, reason: avoid collision after fix types in other method */
        EncodingDetector createComposite2(Class<? extends EncodingDetector> cls, List<EncodingDetector> list, Set<Class<? extends EncodingDetector>> set, Map<String, Param> map, MimeTypes mimeTypes, ServiceLoader serviceLoader) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
            EncodingDetector encodingDetectorNewInstance;
            try {
                encodingDetectorNewInstance = cls.getConstructor(ServiceLoader.class, Collection.class).newInstance(serviceLoader, set);
            } catch (NoSuchMethodException unused) {
                TikaConfig.LOG.debug("couldn't find constructor for service loader + collection for {}", cls);
                encodingDetectorNewInstance = null;
            }
            if (encodingDetectorNewInstance != null) {
                return encodingDetectorNewInstance;
            }
            try {
                return cls.getConstructor(List.class).newInstance(list);
            } catch (NoSuchMethodException unused2) {
                TikaConfig.LOG.debug("couldn't find constructor for EncodingDetector(List) for {}", cls);
                return encodingDetectorNewInstance;
            }
        }
    }

    private static class RendererXmlLoader extends XmlLoader<Renderer, Renderer> {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public Renderer decorate(Renderer renderer, Element element) {
            return renderer;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public Renderer preLoadOne(Class<? extends Renderer> cls, String str, MimeTypes mimeTypes) {
            return null;
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        boolean supportsComposite() {
            return true;
        }

        private RendererXmlLoader() {
            super();
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        /* bridge */ /* synthetic */ Renderer createComposite(Class<? extends Renderer> cls, List<Renderer> list, Set<Class<? extends Renderer>> set, Map map, MimeTypes mimeTypes, ServiceLoader serviceLoader) throws IllegalAccessException, InstantiationException, InvocationTargetException {
            return createComposite2(cls, list, set, (Map<String, Param>) map, mimeTypes, serviceLoader);
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        String getParentTagName() {
            return "renderers";
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        String getLoaderTagName() {
            return "renderer";
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        Class<? extends Renderer> getLoaderClass() {
            return Renderer.class;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public boolean isComposite(Renderer renderer) {
            return renderer instanceof CompositeRenderer;
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        boolean isComposite(Class<? extends Renderer> cls) {
            return CompositeRenderer.class.isAssignableFrom(cls);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public Renderer createDefault(MimeTypes mimeTypes, ServiceLoader serviceLoader) {
            return TikaConfig.getDefaultRenderer(serviceLoader);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        public Renderer createComposite(List<Renderer> list, MimeTypes mimeTypes, ServiceLoader serviceLoader) {
            return new CompositeRenderer(list);
        }

        @Override // org.apache.tika.config.TikaConfig.XmlLoader
        /* renamed from: createComposite, reason: avoid collision after fix types in other method */
        Renderer createComposite2(Class<? extends Renderer> cls, List<Renderer> list, Set<Class<? extends Renderer>> set, Map<String, Param> map, MimeTypes mimeTypes, ServiceLoader serviceLoader) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
            Renderer rendererNewInstance;
            try {
                rendererNewInstance = cls.getConstructor(ServiceLoader.class, Collection.class).newInstance(serviceLoader, set);
            } catch (NoSuchMethodException unused) {
                TikaConfig.LOG.debug("couldn't find constructor for service loader + collection for {}", (Object) null);
                rendererNewInstance = null;
            }
            if (rendererNewInstance != null) {
                return rendererNewInstance;
            }
            try {
                return cls.getConstructor(List.class).newInstance(list);
            } catch (NoSuchMethodException unused2) {
                TikaConfig.LOG.debug("couldn't find constructor for Renderer(List) for {}", cls);
                return rendererNewInstance;
            }
        }
    }
}
