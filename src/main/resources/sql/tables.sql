
create type user_status as enum(
  'ENABLED',
  'DISABLED'
);

create type system_part as enum(
  'CALL_CENTER',
  'DISPATCHER',
  'WORK_GROUP',
  'ADMINISTRATOR',
  'AUTH'
);

create type event_type as enum(
  'INFO',
  'WARNING',
  'ERROR',
  'SECURITY'
);

create type right_type as enum(
  'DENIED',
  'READ',
  'WRITE',
  'SECURITY'
);

create type event_action as enum(
  'CREATE',
  'READ',
  'UPDATE',
  'DELETE',
  'OTHER'
);

create table users (
  id bigint primary key generated always as identity,
  login_name_str text not null,
  password_byte bytea not null,
  user_description_json json,
  user_status_id bigint references user_status (id),
  create_date_dttm timestamp,
  last_login_date_dttm timestamp,
  password_set_date_dttm timestamp
);

create table user_session (
  id bigint primary key generated always as identity,
  user_id bigint references users (id),
  start_session_dttm timestamp,
  end_session_dttm timestamp,
  end_session_dttm timestamp,
  session_token text,
  device_str text,
  ip_str text
);

create table groups (
  id bigint primary key generated always as identity,
  name_str text,
  description_str text
);

create table group_right (
  id bigint primary key generated always as identity,
  system_part_id bigint references system_part (id),
  fieldno_str text,
  field_right_type_id bigint references right_type (id),
  group_id bigint references groups (id),
  title_str text
);

create table settings (
  id bigint primary key generated always as identity,
  user_id bigint references users (id),
  system_part_id bigint references system_part (id),
  settings_json json
);

create table event (
  id bigint primary key generated always as identity,
  user_id bigint references users (id),
  event_type_id bigint references event_type (id),
  system_part_id bigint references system_part (id),
  field_name_str text,
  field_value_before_str text,
  field_value_after_str text,
  change_date_dttm timestamp,
  change_reason_text text,
  event_code_num int,
  description_json json
);