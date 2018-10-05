import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Absence } from './absence.model';
import { AbsenceService } from './absence.service';

@Component({
    selector: 'jhi-absence-detail',
    templateUrl: './absence-detail.component.html'
})
export class AbsenceDetailComponent implements OnInit, OnDestroy {

    absence: Absence;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private absenceService: AbsenceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAbsences();
    }

    load(id) {
        this.absenceService.find(id)
            .subscribe((absenceResponse: HttpResponse<Absence>) => {
                this.absence = absenceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAbsences() {
        this.eventSubscriber = this.eventManager.subscribe(
            'absenceListModification',
            (response) => this.load(this.absence.id)
        );
    }
}
