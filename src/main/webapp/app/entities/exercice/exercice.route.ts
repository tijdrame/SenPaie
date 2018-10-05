import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ExerciceComponent } from './exercice.component';
import { ExerciceDetailComponent } from './exercice-detail.component';
import { ExercicePopupComponent } from './exercice-dialog.component';
import { ExerciceDeletePopupComponent } from './exercice-delete-dialog.component';

@Injectable()
export class ExerciceResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const exerciceRoute: Routes = [
    {
        path: 'exercice',
        component: ExerciceComponent,
        resolve: {
            'pagingParams': ExerciceResolvePagingParams
        },
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.exercice.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'exercice/:id',
        component: ExerciceDetailComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.exercice.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const exercicePopupRoute: Routes = [
    {
        path: 'exercice-new',
        component: ExercicePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.exercice.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'exercice/:id/edit',
        component: ExercicePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.exercice.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'exercice/:id/delete',
        component: ExerciceDeletePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.exercice.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
