const gulp = require('gulp');
const minifyCss = require('gulp-clean-css');
const rename = require('gulp-rename');

gulp.task('minify-css', () => {
    gulp.src('public/styles/*.css')
    .pipe(minifyCss())
    .pipe(rename({
        suffix: '.min'
    }))
    .pipe(gulp.dest('content/style'));
});