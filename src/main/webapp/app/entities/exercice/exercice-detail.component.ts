import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Exercice } from './exercice.model';
import { ExerciceService } from './exercice.service';

@Component({
    selector: 'jhi-exercice-detail',
    templateUrl: './exercice-detail.component.html'
})
export class ExerciceDetailComponent implements OnInit, OnDestroy {

    exercice: Exercice;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private exerciceService: ExerciceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInExercices();
    }

    load(id) {
        this.exerciceService.find(id)
            .subscribe((exerciceResponse: HttpResponse<Exercice>) => {
                this.exercice = exerciceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInExercices() {
        this.eventSubscriber = this.eventManager.subscribe(
            'exerciceListModification',
            (response) => this.load(this.exercice.id)
        );
    }
}
