CREATE FUNCTION "fn_CheckIfUserHasRole"(user_id integer, role_title character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF user_id IS NULL OR user_id = 0 THEN
		RETURN TRUE;
	ELSE
		RETURN ((
			SELECT COUNT(*) FROM users LEFT JOIN roles ON roles.id=users.role_id WHERE users.id=user_id AND roles.id=(
				SELECT id FROM roles WHERE title=role_title LIMIT 1
			)
		) > 0);
	END IF;
END;
$$;

CREATE FUNCTION fn_update_user_image_path() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NEW.image_path IS NULL THEN
        NEW.image_path := '__default__.png';
    END IF;
    RETURN NEW;
END
$$;

CREATE TABLE meetings (
    id integer NOT NULL,
    title character varying NOT NULL,
    date timestamp without time zone NOT NULL,
    description character varying NOT NULL,
    image_path character varying NOT NULL,
    address character varying NOT NULL
);

ALTER TABLE meetings ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME meetings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE moderator_proposals (
    id integer NOT NULL,
    speaker_id integer NOT NULL,
    report_topic_id integer NOT NULL,
    CONSTRAINT chk_moderator_proposals_speaker_id CHECK ("fn_CheckIfUserHasRole"(speaker_id, 'speaker'::character varying))
);

ALTER TABLE moderator_proposals ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME moderator_proposals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE report_topics (
    id integer NOT NULL,
    title character varying NOT NULL,
    meeting_id integer NOT NULL
);

ALTER TABLE report_topics ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME report_topics_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE report_topics_speakers (
    id integer NOT NULL,
    report_topic_id integer NOT NULL,
    speaker_id integer NOT NULL,
    CONSTRAINT chk_report_topics_speakers_speaker_id CHECK ("fn_CheckIfUserHasRole"(speaker_id, 'speaker'::character varying))
);

ALTER TABLE report_topics_speakers ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME report_topics_speakers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE roles (
    id integer NOT NULL,
    title character varying NOT NULL
);

ALTER TABLE roles ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE speaker_proposals (
    id integer NOT NULL,
    speaker_id integer NOT NULL,
    report_topic_id integer NOT NULL,
    CONSTRAINT chk_speaker_proposals_speaker_id CHECK ("fn_CheckIfUserHasRole"(speaker_id, 'speaker'::character varying))
);

ALTER TABLE speaker_proposals ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME speaker_proposals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE topic_proposals (
    id integer NOT NULL,
    speaker_id integer NOT NULL,
    topic_title character varying NOT NULL,
    meeting_id integer NOT NULL,
    CONSTRAINT chk_topic_proposals_speaker_id CHECK ("fn_CheckIfUserHasRole"(speaker_id, 'speaker'::character varying))
);

ALTER TABLE topic_proposals ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME topic_proposals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE users (
    id integer NOT NULL,
    login character varying NOT NULL,
    role_id integer NOT NULL,
    password character varying NOT NULL,
    surname character varying NOT NULL,
    name character varying NOT NULL,
    email character varying NOT NULL,
    image_path character varying DEFAULT 'default_avatar.png'::character varying
);

ALTER TABLE users ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE users_meetings (
    id integer NOT NULL,
    user_id integer NOT NULL,
    meeting_id integer NOT NULL,
    present boolean DEFAULT false NOT NULL
);

ALTER TABLE users_meetings ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME users_meetings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (1, 'Spring Core', '2021-08-21 18:00:00', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab accusantium amet commodi consectetur, dolor, dolores ex explicabo fugit harum in inventore minus obcaecati quam qui quidem quisquam quos repudiandae veniam.', 'spring-core.png', '1st Ave SW Cairo, Georgia(GA)');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (20, 'Android(Частина 3)', '2021-10-20 21:00:00', 'Vivamus hendrerit dignissim purus, non bibendum turpis bibendum eget. Pellentesque finibus eget ex sit amet interdum. Proin euismod ex ut mi tempus condimentum. Morbi a sapien lectus. Fusce accumsan arcu mollis odio dapibus luctus. Aliquam erat volutpat. Praesent nec mauris dapibus, tempor dui id, ultrices metus. Sed sit amet dui blandit, mattis felis quis, volutpat risus.', 'meeting-20.png', 'Cabrillo St Costa Mesa, California');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (24, '.NET Core VS .NET Framework', '2021-10-28 07:00:00', 'Nunc egestas consectetur pretium. Pellentesque quis sollicitudin augue. Ut tincidunt nisi sit amet sem volutpat, nec ornare metus porttitor. Ut auctor purus a dui tincidunt hendrerit. Nam quis mauris elit. Quisque lacinia, tortor a aliquet suscipit, metus augue tempus diam, eu tincidunt odio ante ac ligula. Etiam et lectus venenatis, mattis diam quis, accumsan lectus. Donec a lacus id arcu iaculis faucibus. Cras vulputate nulla augue, quis pulvinar quam venenatis a. Nulla laoreet massa sed varius eleifend.', 'meeting_--T_.png', 'Independence Rd Saginaw, Minnesota');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (21, 'C# Basics', '2021-10-01 10:00:00', 'Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Cras sit amet lacinia orci, ut tincidunt turpis. Vivamus tempor turpis enim, sit amet feugiat erat fermentum ac. Mauris tellus eros, consectetur at aliquet sed, laoreet sed ligula. Quisque nec laoreet neque. Vestibulum ac ante non eros lobortis gravida sed at tellus. Quisque id rutrum sem.', 'c-sharp.png', 'Maple Ave Catskill, New York');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (26, 'Spring Boot веб-сервіси', '2021-10-21 12:00:00', 'Fusce non mi lacus. Sed massa orci, rutrum at dignissim quis, mollis interdum mi. Pellentesque feugiat elementum mollis. Sed pellentesque urna arcu, eu mollis justo maximus eu. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In hac habitasse platea dictumst. Cras nulla lorem, lacinia congue fringilla et, rhoncus vel enim.', 'meeting_2021-09-03T023911638_1.png', 'Paddock Way');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (28, 'Уніфікована мова моделювання(UML)', '2021-10-27 14:50:00', 'Etiam mollis dapibus orci ut fringilla. Morbi pharetra risus magna, eget sodales diam tempus sit amet. Cras bibendum ex in neque volutpat tincidunt. Mauris elit risus, convallis sed augue quis, congue tempus magna. Nulla et suscipit nulla. Proin ornare rhoncus tempus. Duis at tortor est. Nunc nec leo id eros blandit molestie quis ac lectus. Aenean mattis laoreet aliquet.', 'meeting_2021-09-03T025325827_1.png', 'Blossom Hill Rd');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (6, 'JUnit 5', '2021-08-30 12:00:00', 'Etiam volutpat leo ut pharetra semper. Nulla a tortor tempor, rhoncus enim malesuada, suscipit neque. Nulla ac lacus orci. Nunc ultricies nec neque vestibulum bibendum. Nunc odio odio, pellentesque nec dui ut, iaculis ullamcorper lacus. Etiam sollicitudin, nisl ut dignissim hendrerit, enim sapien aliquam purus, sit amet consectetur lectus eros id lacus. Fusce sagittis augue convallis consequat aliquam. Mauris eget massa eget turpis lobortis posuere.', 'junit-5.png', 'Parrish Gap Rd SE Turner, Oregon(OR)');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (12, 'MV Patterns', '2021-12-18 16:20:00', 'Praesent suscipit nulla ut posuere varius. Sed ut metus at libero luctus tempor eget a leo. Proin rutrum metus ut ex dapibus eleifend. Maecenas pharetra, turpis non faucibus pellentesque, ex metus aliquam tellus, non vehicula lectus ipsum blandit dui. Donec at eros nulla. Nam tempor vulputate metus, et sollicitudin erat hendrerit ut. Donec tincidunt condimentum efficitur. Aenean eget tempor eros, quis placerat dolor. Donec eu sagittis orci. Quisque hendrerit porta risus sed lacinia. Quisque rhoncus mattis pharetra. Aliquam sollicitudin massa id malesuada mollis. Nam placerat nisi sit amet lobortis ultricies.', 'mv-patterns.png', 'W 4th St Red Wing, Minnesota(MN)');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (25, 'Основи JavaScript', '2021-10-20 17:00:00', 'Duis nec odio at mi ullamcorper placerat. Praesent in nisl ac felis faucibus placerat et vel nulla. Nullam nisl lacus, ornare in orci vel, tincidunt fermentum libero. Donec quis arcu consequat, condimentum massa ac, sagittis ipsum. Donec auctor, risus in efficitur viverra, dui diam scelerisque dui, ut consequat quam tellus eget eros. Etiam a orci odio. Praesent sed nulla dolor. Vivamus elementum accumsan libero quis cursus.', 'meeting_2021-09-03T002316802_1.jpg', 'Paddock Way');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (4, 'Java Core', '2021-10-30 15:30:00', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Duis condimentum velit dignissim facilisis gravida. Vestibulum consectetur nisl vitae nisl ultrices faucibus. Donec faucibus ut ex sed mattis. Cras nec tortor non mi hendrerit egestas. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam eleifend hendrerit augue, at malesuada dui viverra id.', 'java-core.png', 'Ivy Hill Rd Red Bank, New Jersey(NJ)');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (22, 'iOS(Частина 1)', '2021-10-09 12:00:00', 'Pellentesque fringilla arcu in ex pretium, eget lobortis quam volutpat. Nunc sed molestie dui. Duis in risus vitae augue placerat consequat. Cras pulvinar suscipit fringilla. Proin sit amet vestibulum magna. Praesent at euismod tortor. Nunc sollicitudin odio turpis, et volutpat ipsum ultrices at. Fusce a ex quam. In non rhoncus erat. Vestibulum viverra fermentum pellentesque. In iaculis interdum mattis. Phasellus eget gravida justo.', 'meeting_2021-09-02T215719601_1.jpg', 'North St Norfolk, Massachusetts');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (27, 'iOS(Частина 3)', '2021-10-06 11:00:00', 'Aliquam fringilla eget magna ac sagittis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Nunc lorem enim, tristique vitae tellus eget, porttitor ornare velit. Nunc ac eleifend enim. Morbi porta, nunc vitae faucibus dapibus, risus neque aliquam nibh, eu ultrices urna lorem ut sapien. Nulla facilisi. Vivamus ac ex nisi.', 'meeting_2021-09-03T024458555_1.jpg', 'Mockingbird Ln');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (29, 'Основи Python', '2021-10-07 11:30:00', 'Pellentesque consectetur odio et porta hendrerit. Praesent porttitor posuere erat. Quisque ac nunc et sapien interdum sollicitudin non vitae nulla. Duis sit amet orci sed diam scelerisque ullamcorper. Etiam volutpat odio nibh, vehicula dapibus nibh efficitur id. Phasellus vel eros at magna vulputate suscipit id eget turpis. Cras ut mi leo.', 'meeting_2021-09-03T030253768_1.png', 'White Oak Dr');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (8, 'Git Basics', '2021-08-31 19:00:00', 'Donec sit amet felis egestas, elementum sapien consectetur, porta dolor. Cras libero urna, tristique ac magna quis, malesuada rhoncus mauris. Praesent id interdum lorem, et rutrum diam. Sed tempus non libero ut facilisis. Fusce hendrerit vestibulum dapibus. Donec vitae erat mi. Suspendisse congue sem semper enim dignissim sagittis. Suspendisse hendrerit eu dolor ut pretium. Vivamus ac tincidunt diam. Suspendisse sem velit, laoreet quis sem vel, ornare venenatis felis. Suspendisse potenti. Aliquam maximus est tristique justo molestie, eu gravida metus sodales. Sed massa eros, tincidunt non vulputate ut, fermentum et ligula. Phasellus nec mauris viverra, ullamcorper libero id, tempus lectus. Vivamus dolor tortor, euismod non felis ac, ornare pellentesque ex.', 'git-basics.png', '77th St Jackson Heights, New York(NY)');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (9, 'Grasp Principles', '2021-10-14 09:00:00', 'Praesent molestie accumsan metus. Quisque at ex hendrerit, pulvinar turpis in, fermentum metus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Donec molestie dictum velit, nec gravida nulla placerat id. Etiam lectus justo, interdum convallis ultrices a, pharetra suscipit magna. Ut eget pretium arcu. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vivamus ac venenatis dolor.', 'grasp.png', 'Chip Rd Auburn, Michigan(MI)');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (10, 'REST API', '2021-09-30 11:00:00', 'Quisque quis sem ex. Integer aliquam nisi id magna scelerisque aliquet. Nulla eros felis, varius eu magna sed, auctor ultrices metus. Fusce eget malesuada tortor. Nulla nec mollis massa. Maecenas dapibus malesuada lobortis. Nulla vel ipsum non orci pellentesque pulvinar. In consectetur ut felis vel sodales. Phasellus placerat nulla sit amet libero viverra placerat ut id ante. Mauris sem risus, varius vel pulvinar ullamcorper, euismod quis arcu. Donec vitae consectetur arcu, tincidunt tincidunt libero. Suspendisse eu gravida ipsum. Pellentesque nec felis a ligula venenatis laoreet a sed felis. Suspendisse sagittis sed leo suscipit semper.', 'rest-api.png', 'County 800 Rd E Lerna, Illinois(IL)');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (11, 'Kotlin Basics', '2021-09-02 14:00:00', 'Curabitur consectetur ut erat non molestie. Integer fermentum pulvinar mauris ac mollis. Aliquam sodales erat nisl. Nam eu hendrerit tellus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Mauris laoreet velit sit amet purus lacinia, dictum ullamcorper nulla dignissim. Ut viverra pellentesque lectus, non condimentum augue feugiat vitae. Morbi nec volutpat enim, id auctor magna. Integer bibendum nibh in metus commodo efficitur. In hac habitasse platea dictumst. Donec varius fringilla dui nec egestas. Nulla commodo ipsum sit amet nunc aliquam venenatis. Nullam nisi enim, eleifend in arcu et, feugiat bibendum diam. Proin fringilla hendrerit sem, quis tincidunt felis. Nam ac commodo ligula.', 'kotlin-basics.png', 'Flamingo Dr Mount Morris, Michigan(MI)');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (13, 'SQL Basics', '2021-08-29 13:00:00', 'Morbi at metus ex. Praesent mollis lacus at vehicula sagittis. Vestibulum sit amet ornare odio. Vestibulum accumsan diam lacus, a consequat lectus aliquam quis. Maecenas quis urna at urna malesuada laoreet lacinia non erat. Proin dapibus dapibus urna, id eleifend lectus tincidunt bibendum. Aliquam eget ullamcorper sapien. Vivamus id placerat justo. Fusce porttitor sapien eget condimentum fermentum. Nam bibendum ex id velit venenatis facilisis. Nunc vestibulum massa eu arcu sollicitudin, dictum fringilla orci ornare. In scelerisque elementum mattis. Pellentesque in finibus nulla. Sed gravida felis efficitur, vehicula nisl ut, sollicitudin lorem.', 'sql-basics.png', 'Wilson St Elmhurst, Illinois(IL)');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (14, 'Frontend розробка', '2021-09-23 12:00:00', 'Phasellus ultrices nulla eget fermentum semper. Morbi convallis diam pharetra justo cursus eleifend. Integer tincidunt convallis massa luctus pulvinar. Phasellus malesuada fermentum nunc, vel consequat ipsum varius ut. Morbi mollis erat quis felis efficitur, quis iaculis purus consequat. Praesent eu pharetra urna, id tempus libero. Maecenas placerat, tellus ut imperdiet dignissim, mi massa laoreet tortor, ac iaculis nisi sapien eget justo. Nullam ac viverra ex, eu volutpat libero. Integer lacinia augue neque, sit amet eleifend lorem ultricies vel. Nullam laoreet magna cursus gravida laoreet. Suspendisse quis lacus id ipsum elementum consectetur eget dignissim dui. Phasellus et metus sollicitudin, elementum turpis vitae, dignissim enim. Proin non odio tempor, auctor quam tempor, congue dui. Ut felis arcu, varius sed pharetra non, tempor placerat dui.', 'frontend.png', 'Harry Place Concord');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (16, 'Тестування', '2021-10-15 17:00:00', 'Curabitur ut tincidunt nisi. Nunc dictum risus vel pretium efficitur. Vivamus cursus et massa in tincidunt. Vestibulum fringilla magna non egestas mollis. Curabitur mattis laoreet augue eget faucibus. Nam fermentum, purus in rhoncus posuere, orci justo mollis dui, at elementum orci mi ac enim. Praesent eleifend justo eros, in facilisis mi aliquet nec.', 'qa.png', 'Griffin Street Phoenix');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (15, 'Верстка сайтів', '2021-09-30 13:00:00', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras rutrum tincidunt tortor, eu interdum metus eleifend nec. Donec condimentum mauris vel feugiat mollis. Ut fermentum sem vitae sodales dapibus. Aenean a lectus ut dolor iaculis mollis. Integer eleifend lorem ac ante scelerisque, at pulvinar diam convallis. In non lobortis urna. Cras vitae fermentum lectus.', 'website.png', 'Cedarstone Drive Fostoria');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (17, 'C++ Basics', '2021-10-15 09:00:00', 'Donec fringilla nisl at nisl eleifend, quis varius nisl sagittis. Nunc metus odio, volutpat eu mauris lacinia, dignissim rutrum nibh. In vulputate nisi ut luctus interdum. Ut in congue lorem. In vel pulvinar eros. Phasellus hendrerit cursus diam ut convallis. Aliquam quis dictum sem, eget luctus elit. Praesent quis est vitae velit euismod ultricies.', 'C++.png', 'Avon Ave Wadsworth');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (18, 'Android(Частина 1)', '2021-10-18 21:00:00', 'Donec tincidunt est non suscipit faucibus. Sed vel hendrerit lectus, vel finibus risus. Curabitur semper at ante et porttitor. Maecenas blandit vestibulum nisl at egestas. Aenean feugiat mi dolor, nec consectetur lorem viverra vel. Vestibulum dignissim pretium ex, eget eleifend ante commodo in. Fusce mauris ligula, volutpat sit amet purus in, scelerisque tristique orci. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Suspendisse id feugiat ligula.', 'android-1.png', 'Tyler St Tyler, Minnesota');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (19, 'Android(Частина 2)', '2021-10-19 21:00:00', 'Aenean et ex enim. Duis suscipit semper nibh a dapibus. Ut condimentum orci felis. Integer sit amet est nec ex suscipit tincidunt vitae vel lorem. Donec dignissim velit accumsan, volutpat massa vel, suscipit erat. Curabitur facilisis diam in cursus vulputate. Vivamus viverra, lacus vel condimentum dignissim, elit erat egestas nibh, ut dictum magna purus in quam.', 'android-2.png', 'Lawrimore Rd Hemingway, South Carolina');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (23, 'iOS(Частина 2)', '2021-10-31 15:00:00', 'Mauris eu nulla eu sem vulputate pretium. Nullam ornare purus at metus mattis aliquam. Donec pretium nec nisi eget porta. Curabitur mattis dui faucibus libero semper, eu hendrerit sem interdum. Vivamus non diam eget metus posuere dictum. Etiam aliquet nibh et pulvinar aliquam. Ut ut viverra neque. Nam volutpat molestie nunc, non bibendum metus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Suspendisse eget risus non purus scelerisque rhoncus.', 'meeting_2021-09-02T220246254_1.jpg', 'Maple St Mcpherson, Kansas');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (30, 'Postman', '2021-10-16 11:00:00', 'In sapien lorem, pharetra et ligula in, malesuada cursus magna. Morbi consequat eu ligula sit amet dignissim. Fusce eget elit tellus. Cras dapibus mattis felis id semper. Donec cursus sollicitudin egestas. Proin dictum volutpat orci, nec semper felis aliquam a. Phasellus ipsum mauris, vestibulum in fermentum eleifend, vulputate id quam. Aenean nunc augue, tempus nec erat eu, ultricies lacinia ligula. Fusce orci ex, condimentum sit amet nibh ut, porttitor iaculis libero. Fusce ornare sollicitudin tellus id pulvinar. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In sit amet orci tristique, sollicitudin nunc in, faucibus elit. Suspendisse pretium ut nisl at tincidunt. Nullam sed auctor neque. Ut finibus elit id quam accumsan, in commodo felis aliquet.', 'meeting_2021-09-03T165030729_1.png', 'Homestead Rd');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (7, 'Solid Principles', '2021-09-25 19:30:00', 'Fusce sed ultricies nulla. Praesent venenatis turpis enim, nec mollis ex dictum et. Sed ultrices nulla velit, ut aliquam velit commodo eu. Integer nisi neque, ultrices eget laoreet et, eleifend at mi. In sapien nisl, aliquet ac sodales id, condimentum non leo. Vivamus a libero semper, mollis nisi sed, fringilla neque. Praesent a placerat velit, in fermentum erat. Vestibulum interdum diam ac massa efficitur, at finibus arcu convallis.', 'solid.png', 'River Boat');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (31, 'Java Spring', '2021-10-15 14:00:00', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dignissimos eaque illum, ipsum iste iusto labore praesentium voluptatem? Alias beatae commodi delectus fuga, harum hic impedit iure pariatur saepe soluta suscipit temporibus, tenetur voluptates. Architecto autem culpa deleniti distinctio eius illo itaque iusto magnam, minus quo tenetur, vel vero, vitae. Aperiam aspernatur assumenda, consectetur corporis cum deserunt dolor earum, esse hic, nihil placeat quam sapiente voluptatum. Excepturi fugiat ipsam sed. Animi corporis est explicabo, illo ipsam iure molestiae nemo omnis perspiciatis, porro provident quam rem repellat repellendus sint velit vitae? Animi aut, distinctio doloremque ipsa laboriosam odit provident quaerat repellat soluta.', 'meeting_2021-09-06T192807774_1.png', 'Avon Ave Wadsworth');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (2, 'Spring Data', '2021-10-23 18:00:00', 'Aperiam asperiores aut delectus, enim et exercitationem explicabo facere laudantium libero magni maiores mollitia, natus nemo quaerat quasi suscipit veritatis. Assumenda at beatae, eaque enim fugiat laboriosam molestias nam natus, obcaecati officia perferendis quia quos velit voluptas voluptates? A aperiam asperiores culpa cumque, dolore dolorem doloribus ea eaque est eveniet hic illo ipsum magnam minima nisi perspiciatis placeat quam ratione repellendus voluptatem! Ab, adipisci alias amet aspernatur, corporis delectus dignissimos distinctio doloribus hic laboriosam, minus molestias necessitatibus nesciunt nisi numquam perferendis provident quibusdam reprehenderit repudiandae saepe sed sit ullam ut veritatis voluptate? Deleniti hic itaque odio odit perspiciatis provident sed sequi, velit vitae voluptas. Accusamus ad, alias amet, consequuntur culpa deleniti doloribus eaque esse eum fugiat id, nam omnis placeat porro quis quo veritatis! A accusantium aliquid architecto asperiores, assumenda at corporis cumque ea eligendi enim error et eveniet ex expedita fuga illum, impedit incidunt, iure minima mollitia neque nobis nostrum omnis placeat porro provident quisquam quod ratione rem repellat reprehenderit sed tenetur velit veniam vitae voluptas voluptate. Ad alias autem consectetur culpa debitis dolorem, doloribus esse eum fugit, harum officia perspiciatis quam reprehenderit sunt veniam? Dolorem eius harum nisi nulla, quibusdam quidem sit. Ab accusantium animi consectetur debitis eaque eligendi error eum illo minima nesciunt nobis odit quasi quibusdam quidem rem, saepe, sequi veniam! Consequatur doloribus ea eius, libero nobis nulla quibusdam reprehenderit. Atque commodi cumque dolorem molestias qui quia voluptate voluptatem voluptates. Asperiores aut corporis, enim maiores rerum veritatis? Dolore doloremque eum fuga in nobis officiis repellendus rerum sint, soluta suscipit. Accusantium aliquam at atque consequatur culpa debitis dicta dignissimos dolor dolore dolorem dolorum eaque enim esse exercitationem fugiat iusto laborum maiores nobis non nulla odio, pariatur porro praesentium quam quas, quis rem repellendus soluta sunt suscipit ullam voluptas voluptatem voluptatibus?', 'spring-data.png', '6th St Sully, Iowa(IA)');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (3, 'Spring MVC', '2021-10-30 15:00:00', 'Adipisci aperiam assumenda autem blanditiis distinctio doloremque est et excepturi, fugiat fugit, illo itaque iure nesciunt odit perferendis porro possimus provident quas quibusdam quidem quo quod recusandae veritatis, vitae voluptatem. Aperiam atque beatae commodi consectetur consequuntur deserunt earum eius eligendi exercitationem fugit hic illo illum in ipsum itaque labore laboriosam molestiae nam nobis nostrum obcaecati odit officiis praesentium provident quam quia quibusdam quod ratione recusandae, reprehenderit sapiente sequi similique soluta velit veniam voluptas voluptatem! Dolores ipsa itaque neque perspiciatis reiciendis suscipit. Dignissimos eveniet ex exercitationem mollitia natus necessitatibus odio? Aliquid amet at atque commodi consequatur deleniti deserunt distinctio dolore ducimus ea esse est excepturi explicabo ipsa maiores maxime minus molestias mollitia neque nisi, nobis numquam odio pariatur placeat quod sapiente, sint sunt vel voluptas voluptatibus. A animi debitis delectus dolore doloremque eos est, excepturi expedita in, ipsum iste itaque laborum minima optio perferendis perspiciatis provident quae quibusdam quos recusandae repellendus temporibus vero voluptatum! Accusamus architecto culpa deleniti, dignissimos distinctio hic impedit laboriosam nemo nihil nobis omnis perferendis placeat provident qui quibusdam, quis quod saepe soluta temporibus vero.', 'spring-mvc.png', 'Weiss St Saginaw, Michigan(MI)');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (5, 'Design Patterns', '2021-10-28 21:00:00', 'Nullam gravida vitae dolor id tincidunt. Maecenas porta ligula turpis, eget dignissim lorem ultricies a. Cras leo augue, venenatis et imperdiet et, dapibus sed sem. Sed dignissim tincidunt turpis, et finibus leo tincidunt eget. Donec auctor finibus aliquet. Praesent pretium congue ipsum, et luctus mi accumsan interdum. Nam aliquam sollicitudin metus sed dictum. Nunc ex ligula, malesuada et facilisis quis, hendrerit eget orci. Maecenas venenatis metus vitae fermentum laoreet. Donec quis tortor vestibulum, dignissim lectus non, viverra urna. Donec tincidunt consequat arcu in tempus.', 'design-patterns.png', 'Timber Wolf Trail');
INSERT INTO meetings (id, title, date, description, image_path, address) OVERRIDING SYSTEM VALUE VALUES (32, 'C# WPF', '2021-10-23 12:30:00', 'Donec vulputate scelerisque sem, id viverra nisl tempus et. Nam aliquet purus lorem, a congue neque iaculis et. Integer dapibus lacinia mattis. Morbi et eros in tellus consectetur luctus eget eu ipsum. Nunc elit ante, scelerisque eu cursus venenatis, bibendum eu dolor. Phasellus non nisl tincidunt, sagittis ligula non, sodales orci. Quisque molestie arcu nec elementum placerat', 'meeting_2021-09-28T182625800_1.png', 'new meeting address');

INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (1, 2, 12);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (3, 3, 12);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (4, 10, 12);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (5, 9, 12);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (6, 3, 27);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (7, 4, 27);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (8, 14, 27);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (9, 10, 27);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (11, 4, 25);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (12, 10, 25);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (13, 13, 25);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (14, 3, 25);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (16, 13, 12);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (17, 2, 46);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (22, 14, 44);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (23, 14, 47);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (28, 4, 61);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (29, 10, 61);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (31, 13, 61);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (32, 2, 61);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (34, 3, 70);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (35, 9, 70);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (36, 4, 70);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (37, 14, 70);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (39, 10, 70);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (40, 13, 70);
INSERT INTO moderator_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (41, 2, 72);

INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (1, 'BeanDefinitionReaders', 1);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (58, 'Що таке REST', 10);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (3, 'FactoryBean', 1);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (59, 'Classes and Objects', 4);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (5, 'BeanPostProcessor', 1);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (12, 'OOP Principles', 4);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (60, 'What is Spring', 31);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (2, 'BeanFactoryPostProcessor', 1);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (61, 'Основи Spring', 31);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (62, 'Spring Security', 31);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (63, 'Spring MVC', 31);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (64, 'Crud Repository Interface', 2);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (65, 'JpaRepository Inteface', 2);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (66, 'Query Annotation', 2);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (31, 'Interface Segregation Principle', 7);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (69, 'Why do we need MV-patterns', 3);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (30, 'Liskov Substitution Principle', 7);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (29, 'Open-Closed Principle', 7);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (28, 'Single Responsibility Principle', 7);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (32, 'Dependency Inversion Principle', 7);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (23, 'Inheritance', 4);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (68, 'What are MV-patterns', 3);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (33, 'Multithreading', 4);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (4, 'BeanFactory', 1);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (26, 'Interfaces vs Abstract classes', 4);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (70, 'MVVM example', 3);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (25, 'Polymorphism', 4);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (71, 'MVP example', 3);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (34, 'What are design patterns?', 5);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (72, 'C# Basics', 32);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (35, 'Why do we need design patterns?', 5);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (24, 'Abstraction', 4);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (36, 'Creational Patterns', 5);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (27, 'Method overloading', 4);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (37, 'Introduction', 11);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (38, 'Hello, World!', 11);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (39, 'Kotlin Basics', 11);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (40, 'Program Structure', 11);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (41, 'Variables', 11);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (42, 'Console I/O', 11);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (43, 'Numbers Operations', 11);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (45, 'Loops', 11);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (46, 'Sequences', 11);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (47, 'Arrays', 11);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (44, 'Conditions', 11);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (49, 'Functional Programming', 11);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (55, 'Основи HTML5', 15);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (56, 'Основи CSS3', 15);
INSERT INTO report_topics (id, title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (57, 'Variables', 25);

INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (1, 1, 2);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (2, 2, 4);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (3, 3, 3);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (4, 4, 10);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (5, 5, 10);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (6, 23, 10);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (8, 26, 10);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (9, 28, 2);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (10, 29, 9);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (11, 30, 3);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (12, 31, 9);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (13, 32, 4);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (14, 33, 4);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (17, 34, 2);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (25, 36, 14);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (26, 24, 4);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (27, 35, 14);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (30, 37, 14);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (31, 38, 14);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (33, 46, 14);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (35, 47, 2);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (36, 45, 2);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (37, 12, 10);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (38, 39, 14);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (39, 60, 14);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (40, 62, 2);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (41, 61, 14);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (42, 55, 14);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (43, 25, 14);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (46, 69, 13);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (48, 68, 14);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (54, 70, 2);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (56, 71, 2);
INSERT INTO report_topics_speakers (id, report_topic_id, speaker_id) OVERRIDING SYSTEM VALUE VALUES (58, 72, 14);

INSERT INTO roles (id, title) OVERRIDING SYSTEM VALUE VALUES (1, 'moderator');
INSERT INTO roles (id, title) OVERRIDING SYSTEM VALUE VALUES (2, 'speaker');
INSERT INTO roles (id, title) OVERRIDING SYSTEM VALUE VALUES (3, 'user');

INSERT INTO speaker_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (1, 14, 24);
INSERT INTO speaker_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (2, 14, 26);
INSERT INTO speaker_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (3, 13, 26);
INSERT INTO speaker_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (4, 10, 26);
INSERT INTO speaker_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (9, 10, 24);
INSERT INTO speaker_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (10, 13, 24);
INSERT INTO speaker_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (16, 2, 37);
INSERT INTO speaker_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (17, 2, 39);
INSERT INTO speaker_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (19, 2, 43);
INSERT INTO speaker_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (20, 2, 41);
INSERT INTO speaker_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (24, 14, 40);
INSERT INTO speaker_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (25, 14, 41);
INSERT INTO speaker_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (26, 14, 43);
INSERT INTO speaker_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (28, 14, 63);
INSERT INTO speaker_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (29, 14, 62);
INSERT INTO speaker_proposals (id, speaker_id, report_topic_id) OVERRIDING SYSTEM VALUE VALUES (31, 14, 71);

INSERT INTO topic_proposals (id, speaker_id, topic_title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (5, 2, 'Access Modifiers', 4);
INSERT INTO topic_proposals (id, speaker_id, topic_title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (6, 2, 'Static Members', 4);
INSERT INTO topic_proposals (id, speaker_id, topic_title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (7, 2, 'Git Basics', 8);
INSERT INTO topic_proposals (id, speaker_id, topic_title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (8, 2, 'Git merging', 8);
INSERT INTO topic_proposals (id, speaker_id, topic_title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (9, 14, 'Entity Annotation', 2);
INSERT INTO topic_proposals (id, speaker_id, topic_title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (12, 13, 'Transactional Annotation', 2);
INSERT INTO topic_proposals (id, speaker_id, topic_title, meeting_id) OVERRIDING SYSTEM VALUE VALUES (17, 2, 'Що таке тестування', 16);

INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (4, 'timbereddeviate', 2, '$2a$10$AqlXWcbzii/wSt/81ra58er9n2fEwBm9SCPWNYuHrNxEHavtZgqNm', 'Olson', 'Darrell', 'jyns-fl@assrec.com', 'timbereddeviate.png');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (5, 'schematicheady', 3, '$2a$10$t8BBz85Sg56cp7PwSwrcVOwj2DoIaDY3GnM60S.X99f2ETAtqsgsy', 'Evans', 'Edwin', 'trocadamente@devej.site', 'schematicheady.png');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (6, 'shoemaker', 3, '$2a$10$YWrAP5d4uWf3k7PsrEeY9.92Dj0rPHbvXRhqepvmOl8N4hG.Y.oai', 'Scott', 'Clarence', 'jyns--fl@assrec.com', 'shoemaker.png');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (7, 'crowded', 3, '$2a$10$1898UVrTig9VbW0/gshBqOlSrr/mw//jtaADH/y3l1R6mnGdNw98O', 'Ray', 'Katherine', 'yahmedtemo12h@iamsp.ga', 'crowded.png');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (8, 'glassarty', 3, '$2a$10$tewWlOQrovrePUfD6weDj.3ID8wS.R3HX6V/IvV6nyJ.LerJeJ4XG', 'Mitchelle', 'April', 'pjhon@codx.site', 'user_avatar_2021-09-03T001922495_8.jpg');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (9, 'guise', 2, '$2a$10$9ls0FNi53FZEdVUSEzT.QOvQZcoJ24jps4QEKb5YBZIMaPHFPD5.K', 'Diaz', 'April', 'kmoussa.plagef@khoantuta.com', 'guise.png');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (10, 'rubella', 2, '$2a$10$BwP8nEKm8iCa..NMBCAtm.2B1wpdy/GSz/2MnR4c7pNRHCVMjm3Py', 'Bowman', 'Stephen', '9cimo.gallo7@baanr.com', 'rubella.png');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (11, 'user', 3, '$2a$10$a87f2lua4jfIcsuBVNjaYOLsDePuljMG1FJRvE8a17yHrCL2LhvYG', 'Gilbert', 'Arthur', 'csam@devep.site', 'user.png');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (12, 'penguin', 3, '$2a$10$J/PFVUPvIhPc23t2yfIOReTXSFl5b/CW1Z.3eAO8YA9XigV0b30J6', 'Cole', 'Nina', 'nina.cole@example.com', 'penguin.png');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (13, 'stream', 2, '$2a$10$Yxq4nskFk/bvG6FMZLiUKuVVPx1V3GN5Zbbok.wPTGDFPpGAR7e0.', 'Ross', 'Kent', 'kent.ross@example.com', '__default__.png');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (16, 'suzanne', 3, '$2a$10$QdelGtv0NjdxLlAU3DCLs.zbiHbSmVPxY7hKmEQMoSp5fuJRviKIu', 'Ford', 'Suzanne', 'suzanne@email.com', '__default__.png');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (19, 'tyrone', 3, '$2a$10$zeN38zkv65c3N23NVUESM.9VGe.uwKWu3jMhwmw7LzJtODJfAC5.m', 'Richards', 'Tyrone', 'tyrone@rich.mail', '__default__.png');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (20, 'jordan', 3, '$2a$10$As6OW3LbPUrnmnYfoz3JcO50K/o4QUoPUVu.gjIMIJ1zagduT0r1q', 'Jordan', 'Wyatt', 'jordan@test.mail', '__default__.png');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (21, 'survive', 3, '$2a$10$nzSaL9yI2Eym.RQZ5vwM9OpZexWWM3ensThlQkdw7k6xIdod0oI0e', 'Flores', 'Rhonda', 'survive@example.com', 'user_avatar_2021-09-04T202137914_21.jpg');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (2, 'andrii', 2, '$2a$10$EJppLh1fXvlMlkDXPRKbtOLql1TEpUxdcTcnefwv1TzMdyDQ3Ut5.', 'Босик', 'Андрій', 'andrii.bosyk@example.com', 'user_avatar_2021-09-28T180316945_2.png');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (14, 'speaker', 2, '$2a$10$B5FIUTcKjBf2Cf2B6A3dq.NPpmDOwt6ynFc22hqcSPwq6RaqXamSm', 'Bailey', 'Rodney', 'speaker@example.com', 'user_avatar_2021-09-28T180734340_14.jpg');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (1, 'admin', 1, '$2a$10$OKtwzcDvCjMsF9r9AlkCu.WoJukKbvQrHrW3asRa2FgI9Ty.Xq88K', 'Admin', 'Moderator', 'admin.admin@example.com', 'user_avatar_--T_.jpg');
INSERT INTO users (id, login, role_id, password, surname, name, email, image_path) OVERRIDING SYSTEM VALUE VALUES (3, 'loanGANTRY', 2, '$2a$10$ZSc0e7RLwumCcRa7Pqt2DuwlNkz7Ye5oMuKcNlLsGp22BoiH5NaPe', 'Moore', 'Noah', 'oamira.a0@cerdikiawan.me', 'loanGANTRY.png');

INSERT INTO users_meetings (id, user_id, meeting_id, present) OVERRIDING SYSTEM VALUE VALUES (3, 7, 5, false);
INSERT INTO users_meetings (id, user_id, meeting_id, present) OVERRIDING SYSTEM VALUE VALUES (5, 11, 4, true);
INSERT INTO users_meetings (id, user_id, meeting_id, present) OVERRIDING SYSTEM VALUE VALUES (2, 8, 1, false);
INSERT INTO users_meetings (id, user_id, meeting_id, present) OVERRIDING SYSTEM VALUE VALUES (1, 11, 1, true);
INSERT INTO users_meetings (id, user_id, meeting_id, present) OVERRIDING SYSTEM VALUE VALUES (4, 11, 3, true);

ALTER TABLE users_meetings
    ADD CONSTRAINT chk_users_meetings_user_id CHECK ("fn_CheckIfUserHasRole"(user_id, 'user'::character varying)) NOT VALID;

ALTER TABLE ONLY meetings
    ADD CONSTRAINT meetings_pkey PRIMARY KEY (id);

ALTER TABLE ONLY moderator_proposals
    ADD CONSTRAINT moderator_proposals_pkey PRIMARY KEY (id);

ALTER TABLE ONLY report_topics
    ADD CONSTRAINT report_topics_pkey PRIMARY KEY (id);

ALTER TABLE ONLY report_topics_speakers
    ADD CONSTRAINT report_topics_speakers_pkey PRIMARY KEY (id);

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);

ALTER TABLE ONLY speaker_proposals
    ADD CONSTRAINT speaker_proposals_pkey PRIMARY KEY (id);

ALTER TABLE ONLY topic_proposals
    ADD CONSTRAINT topic_proposals_pkey PRIMARY KEY (id);

ALTER TABLE ONLY users_meetings
    ADD CONSTRAINT unique_record UNIQUE (user_id, meeting_id);

ALTER TABLE ONLY report_topics_speakers
    ADD CONSTRAINT unique_report_topic UNIQUE (report_topic_id);

ALTER TABLE ONLY speaker_proposals
    ADD CONSTRAINT unique_speaker_proposal_record UNIQUE (speaker_id, report_topic_id);

ALTER TABLE ONLY users
    ADD CONSTRAINT unique_users_record_by_email UNIQUE (email);

ALTER TABLE ONLY users
    ADD CONSTRAINT unique_users_record_by_login UNIQUE (login);

ALTER TABLE ONLY users_meetings
    ADD CONSTRAINT users_meetings_pkey PRIMARY KEY (id);

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

CREATE TRIGGER user_image_path_trigger BEFORE INSERT ON users FOR EACH ROW EXECUTE FUNCTION fn_update_user_image_path();

ALTER TABLE ONLY moderator_proposals
    ADD CONSTRAINT fk_moderator_proposals_to_report_topics FOREIGN KEY (report_topic_id) REFERENCES report_topics(id);

ALTER TABLE ONLY moderator_proposals
    ADD CONSTRAINT fk_moderator_proposals_to_users FOREIGN KEY (speaker_id) REFERENCES users(id);

ALTER TABLE ONLY report_topics_speakers
    ADD CONSTRAINT fk_report_topics_speakers_to_report_topics FOREIGN KEY (report_topic_id) REFERENCES report_topics(id);

ALTER TABLE ONLY report_topics_speakers
    ADD CONSTRAINT fk_report_topics_speakers_to_users FOREIGN KEY (speaker_id) REFERENCES users(id);

ALTER TABLE ONLY report_topics
    ADD CONSTRAINT fk_report_topics_to_meetings FOREIGN KEY (meeting_id) REFERENCES meetings(id);

ALTER TABLE ONLY speaker_proposals
    ADD CONSTRAINT fk_speaker_proposals_to_report_topics FOREIGN KEY (report_topic_id) REFERENCES report_topics(id) NOT VALID;

ALTER TABLE ONLY speaker_proposals
    ADD CONSTRAINT fk_speaker_proposals_to_users FOREIGN KEY (speaker_id) REFERENCES users(id);

ALTER TABLE ONLY topic_proposals
    ADD CONSTRAINT fk_topic_proposals_to_meetings FOREIGN KEY (meeting_id) REFERENCES meetings(id) NOT VALID;

ALTER TABLE ONLY topic_proposals
    ADD CONSTRAINT fk_topic_proposals_to_users FOREIGN KEY (speaker_id) REFERENCES users(id);

ALTER TABLE ONLY users_meetings
    ADD CONSTRAINT fk_users_meetings_to_meetings FOREIGN KEY (meeting_id) REFERENCES meetings(id);

ALTER TABLE ONLY users_meetings
    ADD CONSTRAINT fk_users_meetings_to_users FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_users_to_roles FOREIGN KEY (role_id) REFERENCES roles(id);