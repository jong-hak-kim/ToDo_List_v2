package com.todo.domain;

import lombok.Getter;

@Getter
public class TodoEditor {

    private String title;
    private String content;

    public TodoEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static TodoEditorBuilder builder() {
        return new TodoEditorBuilder();
    }

    public static class TodoEditorBuilder {
        private String title;
        private String content;

        TodoEditorBuilder() {
        }

        public TodoEditorBuilder title(final String title) {
            if (title != null) {
                this.title = title;
            }
            return this;
        }

        public TodoEditorBuilder content(final String content) {
            if (content != null) {
                this.content = content;
            }
            return this;
        }

        public TodoEditor build() {
            return new TodoEditor(this.title, this.content);
        }

        public String toString() {
            return "ToDoEditor.ToDoEditorBuilder(title=" + this.title + ", content=" + this.content + ")";
        }
    }
}
