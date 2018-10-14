import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TypeAbsence } from './type-absence.model';
import { TypeAbsenceService } from './type-absence.service';

@Component({
    selector: 'jhi-type-absence-detail',
    templateUrl: './type-absence-detail.component.html'
})
export class TypeAbsenceDetailComponent implements OnInit, OnDestroy {

    typeAbsence: TypeAbsence;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private typeAbsenceService: TypeAbsenceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTypeAbsences();
    }

    load(id) {
        this.typeAbsenceService.find(id)
            .subscribe((typeAbsenceResponse: HttpResponse<TypeAbsence>) => {
                this.typeAbsence = typeAbsenceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTypeAbsences() {
        this.eventSubscriber = this.eventManager.subscribe(
            'typeAbsenceListModification',
            (response) => this.load(this.typeAbsence.id)
        );
    }
}
