package com.todo.domain;

import lombok.Getter;

@Getter
public class ToDoEditor {

    private String title;
    private String content;

    public ToDoEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static ToDoEditorBuilder builder() {
        return new ToDoEditorBuilder();
    }

    public static class ToDoEditorBuilder {
        private String title;
        private String content;

        ToDoEditorBuilder() {
        }

        public ToDoEditorBuilder title(final String title) {
            if (title != null) {
                this.title = title;
            }
            return this;
        }

        public ToDoEditorBuilder content(final String content) {
            if (content != null) {
                this.content = content;
            }
            return this;
        }

        public ToDoEditor build() {
            return new ToDoEditor(this.title, this.content);
        }

        public String toString() {
            return "ToDoEditor.ToDoEditorBuilder(title=" + this.title + ", content=" + this.content + ")";
        }
    }
}
