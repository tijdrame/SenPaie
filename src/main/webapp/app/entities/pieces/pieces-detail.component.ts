import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Pieces } from './pieces.model';
import { PiecesService } from './pieces.service';

@Component({
    selector: 'jhi-pieces-detail',
    templateUrl: './pieces-detail.component.html'
})
export class PiecesDetailComponent implements OnInit, OnDestroy {

    pieces: Pieces;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private piecesService: PiecesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPieces();
    }

    load(id) {
        this.piecesService.find(id)
            .subscribe((piecesResponse: HttpResponse<Pieces>) => {
                this.pieces = piecesResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPieces() {
        this.eventSubscriber = this.eventManager.subscribe(
            'piecesListModification',
            (response) => this.load(this.pieces.id)
        );
    }
}
